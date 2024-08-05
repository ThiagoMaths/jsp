  'use strict';
  $(document).ready(function() {
      $('.n-list-demo div div,.n-list-demo div .outer-ellipsis').on('click', function() {
          var font_class = ($(this).children().attr('class'));
          $('.modal-n').modal('show');
          $('#n').removeClass();
          $('#n').addClass(font_class);
          $('#name').val(font_class);
          $('#code').val('<i class="' + font_class + '"></i>');
      });
  });
