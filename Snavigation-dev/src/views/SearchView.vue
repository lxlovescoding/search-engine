<template>
  <!--  导航栏-->
  <NavBar/>
  <!--  内容-->
  <n-grid cols="24">
    <n-grid-item span="3">
      <div class="green">
      </div>
    </n-grid-item>
    <n-grid-item span="12">

      <div style="font-family: Poppins-regular, si-yuan, sans-serif; color: grey">
        <n-statistic label="一共" tabular-nums>
          <n-number-animation ref="numberAnimationInstRef" :from="0" :to="len"/>
          <template #suffix>
            条结果...
          </template>
        </n-statistic>
      </div>
      <div v-for="image in data" :key="image.url">
        <n-card hoverable :bordered="true" :title="image.caption" style="font-family: si-yuan, sans-serif; background-color: ;">
          <img :src="image.url" style="height: 200px" :alt="image.url"> <br/>
          <template #footer>
            <n-ellipsis style="max-width: 400px">
              {{ image.url }}
            </n-ellipsis>
          </template>
          <template #action>
            <div class="float-end" style="font-size: 16px; font-family: Poppins-regular, sans-serif">
              {{"tidif_value = " + image.tidifvalue}}
            </div>
          </template>
        </n-card>
        <n-divider/>
      </div>
    </n-grid-item>
    <n-grid-item span="1" />
    <!--    推荐此条-->
    <n-grid-item span="8">
      <n-card hoverable :bordered="false" style="margin-top: 30px;">
        <n-list style="font-family: Poppins-regular, si-yuan, sans-serif">
          <template #header>
            <p style="font-size: 30px; font-weight: bold">相关内容</p>
          </template>
          <n-list-item v-for="suggestion in suggestions" :key="suggestion.id">
            <router-link :to="'/search/' + suggestion.caption"
                         style="text-decoration: none; color: cornflowerblue; font-size: 18px">
              {{ suggestion.id + ". " + suggestion.caption }}
            </router-link>
          </n-list-item>
        </n-list>
      </n-card>
    </n-grid-item>
  </n-grid>
  <!--  加载中-->
  <div id="loading" v-if="!loadStatus">
    <img src="/icon/logo.png" alt="logo" class="logo"/>
    <span class="tip">稍等....</span>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {useRoute} from "vue-router";
import {NCard, NGrid, NGridItem, NList, NListItem, NStatistic, NNumberAnimation, NDivider, NEllipsis} from 'naive-ui';
import axios from "axios";
import {getSearchSuggestions} from "@/api/index.js";
import NavBar from "@/components/NavBar.vue";


const route = useRoute();
let data = ref(null);
let loadStatus = ref(false);
let suggestions = ref([]);
let len = ref(0);

getSearchSuggestions(route.params.id).then((res) => {
  let resp = Array.from(res);
  for (let i in resp) {
    suggestions.value.push({
      id: Number(i) + 1,
      caption: resp[i]
    });
  }
  console.log(suggestions.value);
});

axios.get(`http://localhost:8888/search?keyword=${route.params.id}&pageNum=1`).then(function (resp) {
  data.value = resp;
  len.value = data.value.length;
  setTimeout(() => {
    loadStatus.value = true;
  }, 100);
});

</script>

<style lang="scss" scoped>

* {
  //background-color: #efefef;
}

#main,
#loading {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  &.main-normal,
  &.main-focus {
    .main-box {
      opacity: 0;
      margin-top: 0;
      transform: scale(0.35);
      pointer-events: none;
    }
  }

  &.main-box,
  &.main-set {
    .main-box {
      opacity: 1;
      margin-top: 20vh;
      transform: scale(1);
      visibility: visible;
      @media (max-width: 478px) {
        margin-top: 22vh;
      }
    }

    .search-input {
      :deep(.all) {
        opacity: 0;
        width: 0;
        visibility: hidden;
      }
    }
  }

  .all-controls {
    position: fixed;
    width: 100%;
    top: 0;
    padding: 10px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;

    .change-status {
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 26px;
      padding: 8px;
      border-radius: 8px;
      color: var(--main-text-color);
      z-index: 1;
      transition: opacity 0.3s, background-color 0.3s, transform 0.3s;

      &:hover {
        backdrop-filter: blur(20px);
        background-color: var(--main-background-light-color);
      }

      &:active {
        transform: scale(0.95);
      }
    }
  }
}

#loading {
  color: var(--main-text-color);

  .logo {
    width: 100px;
    height: 100px;
    margin-bottom: 24px;
    animation: logo-breathe 3s infinite alternate;
  }

  .tip {
    font-size: 20px;
  }
}

.white-font {
  color: white;
}
</style>
