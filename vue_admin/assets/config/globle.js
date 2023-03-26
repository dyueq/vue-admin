import Vue from "vue";

Vue.mixin({
    methods: {
        hasAuthority(pram) {
            let authority = this.$store.state.tab.authorities
            return authority.indexOf(pram) > -1
        }
    }
})