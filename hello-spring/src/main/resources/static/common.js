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

  // 글 수정 이벤트
  $("form.modify-form")
    .find(".modify-save")
    .on("click", function () {
      var invalidInputs = $("input:invalid, textarea:invalid");
      // 입력값이 없는 속성(invalid)의 개수 > 0
      if (invalidInputs.length > 0) {
        // return; 전송하지 않는다.
        return;
      }

      // 브라우저에서 id값을 얻어온다.
      var id = $("form.modify-form").find("input[type=hidden]").val();

      // 필수 입력값 체크
      // (가정)모든 내용을 작성해야지만 전송이 가능하도록 한다.
      $("form.modify-form")
        .attr({
          // 객체 리터럴 타입
          method: "POST",
          action: "/board/modify/" + id,
        })
        .submit();
    });
    
    // 회원 가입 이벤트
    $(".member-regist-form").find(".cancel-button")
       .on("click", function(){
        history.back();
      });
                        
    $(".member-regist-form").find(".regist-button")
      .on("click", function(){
        $(".member-regist-form")
        .attr({
         // 객체 리터럴 타입
         method: "POST",
         action: "/member/regist"
       })
       .submit();
      });
    


});
