<template>
  <nav class="navbar navbar-expand-lg" data-bs-theme="dark"
       style="background-color: #FFFFFF; box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.15);">
    <div class="container-fluid align-items-center">
      <router-link :to="{name: 'home'}" class="navbar-brand d-flex"
      >
        <img src="../../public/icon/logo.png" alt="Logo" width="32" height="32" class="d-inline-block"
             style="margin-top: 3px; margin-right: 10px">
        <p style="color: #333333; font-family: si-yuan,serif; font-weight: bold; font-size: larger">图搜</p>
      </router-link>
    </div>
  </nav>
</template>

<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap'
</script>
