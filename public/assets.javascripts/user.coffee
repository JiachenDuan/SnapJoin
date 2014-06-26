$ ->
  $.get "/photos", (data) ->
    $.each data, (index,photo) ->
      $("#photos").append $("<li>").text "ddddddddddddddddddddddddddd"

