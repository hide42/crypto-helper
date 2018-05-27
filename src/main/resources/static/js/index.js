new Vue({
    el: '#demo',

    data: {
        sortKey: 'name',

        reverse: false,

        search: '',

        columns: ['name', 'price', 'p1hour','p24hour','p7days'],

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