new Vue({
    el: '#demo',

    data: {
        sortKey: 'market',

        reverse: false,

        search: '',

        columns: ['market','pair','priceA','priceB','coef'],

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