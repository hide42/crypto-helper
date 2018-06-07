new Vue({
    el: '#demo',

    data: {
        sortKey: 'market',

        reverse: false,

        search: '',

        columns: ['market','price','pair','volume'],

        newUser: {},

        crypts:  items

    },

    methods: {
        sortBy: function(sortKey) {
            this.reverse = (this.sortKey == sortKey) ? ! this.reverse : false;
            console.log(sortKey);
            this.sortKey = sortKey;
        }
    }
});