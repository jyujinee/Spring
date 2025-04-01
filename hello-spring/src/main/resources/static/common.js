$().ready(function () {
  // 글 작성 페이지 이벤트
  $("form.write-form")
    .find(".write-save")
    .on("click", function () {
      var invalidInputs = $("input:invalid, textarea:invalid");
      // 입력값이 없는 속성(invalid)의 개수 > 0
      if (invalidInputs.length > 0) {
        // return; 전송하지 않는다.
        return;
      }
      // 필수 입력값 체크
      // (가정)모든 내용을 작성해야지만 전송이 가능하도록 한다.
      $("form.write-form")
        .attr({
          // 객체 리터럴 타입
          method: "POST",
          action: "/board/write",
        })
        .submit();
    });
});
