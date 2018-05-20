new Vue({
  el: '#demo',

  data: {
    sortKey: 'name',

    reverse: false,

    search: '',

    columns: ['name', 'price', '1hr','24hr','7days'],

    newUser: {},

    crypts:  items

  },

  methods: {
    sortBy: function(sortKey) {
      this.reverse = (this.sortKey == sortKey) ? ! this.reverse : false;

      this.sortKey = sortKey;
    }
  }
});
