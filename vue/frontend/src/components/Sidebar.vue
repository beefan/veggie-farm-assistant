<template>
    <div class="sidebar" @click="closeSidebarPanel">
        <div class="sidebar-backdrop" @click="closeSidebarPanel" v-if="isPanelOpen"></div>
        <transition name="slide">
            <div v-if="isPanelOpen"
                 class="sidebar-panel">
                <slot></slot>
            </div>
        </transition>
    </div>
</template>
<script>
    import { store, mutations } from '@/store.js'

    export default {
        methods: {
            closeSidebarPanel: mutations.toggleNav
        },
        computed: {
            isPanelOpen() {
                return store.isNavOpen
            }
        }
    }
</script>

<style>
    .slide-enter-active,
    .slide-leave-active
    {
        transition: transform 0.2s ease;
    }

    .slide-enter,
    .slide-leave-to {
        transform: translateX(100%);
        transition: all 150ms ease-in 0s
    }

    .sidebar-backdrop {
        background-color: rgba(0,0,0,.5);
        width: 100vw;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        cursor: pointer;
    }

    .sidebar-panel {
        overflow-y: auto;
        background-color: #130f40;
        position: fixed;
        text-align:right;
        right:0;
        top: 0;
        height: 100vh;
        z-index: 999;
        padding: 3rem 20px 2rem 20px;
        width: 180px;
        font-family: 'Muli', sans-serif;
    }
    @import url('https://fonts.googleapis.com/css2?family=Muli:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
</style>