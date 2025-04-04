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
         .on("click", function() {
            history.back();
         });
      
      $(".member-regist-form").find(".regist-button")
         .on("click", function() {
            $(".member-regist-form").attr({
                method: "POST",
                action: "/member/regist"
            }).submit();
         });
         
      // 회원가입 비밀번호 패턴 체크
      $(".member-regist-wrapper").find("#password, #confirmPassword")
        .on("keyup", function() {
            // 대문자, 소문자, 숫자, 특수기호 포함해서 10자리 이상.
            var passwordRegExr = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\W).{10,}$/;
            var value = $(this).val();
            
            // 패스워드가 정규표현식의 패턴과 일치하는지 검사.
            var match = passwordRegExr.test(value);
            
            if (!match) {
                var existsPasswordPatternError = $(this).closest("div").find(".passwordPatternError");
                if (existsPasswordPatternError.length == 0) {
                    var message = "비밀번호는 영소문자, 대문자, 숫자, 특수문자를 포함해 10자리 이상으로 입력해야 합니다.";
                    var errorDom = $("<div></div>");
                    errorDom.text(message);
                    errorDom.addClass("error");
                    errorDom.addClass("passwordPatternError");
                    
                    $(this).after(errorDom);
                }
            }
            else {
                var isErrorDom = $(this).closest("div").find(".passwordPatternError");
                if (isErrorDom) {
                    $(this).closest("div").find(".passwordPatternError").remove();
                }
            }
            
            // 패스워드 일치검사.
            var password = $(".member-regist-wrapper").find("#password").val();
            var confirmPassword = $(".member-regist-wrapper").find("#confirmPassword").val();
            
            if (password != confirmPassword) {
                var isPasswordEqualError = $(".member-regist-wrapper").find("#password")
                                                .closest("div").find(".passwordEqualError");
                var isConfirmPasswordEqualError = $(".member-regist-wrapper").find("#confirmPassword")
                                                .closest("div").find(".passwordEqualError");
                
                if (isPasswordEqualError.length > 0 && isConfirmPasswordEqualError.length > 0) {
                    return;
                }
                
                var passwordEqualErrorMessage = "비밀번호가 일치하지 않습니다.";
                var passwordEqualErrorDom = $("<div></div>");
                passwordEqualErrorDom.text(passwordEqualErrorMessage);
                passwordEqualErrorDom.addClass("error");
                passwordEqualErrorDom.addClass("passwordEqualError");
                
                var confirmPasswordEqualErrorDom = $("<div></div>");
                confirmPasswordEqualErrorDom.text(passwordEqualErrorMessage);
                confirmPasswordEqualErrorDom.addClass("error");
                confirmPasswordEqualErrorDom.addClass("passwordEqualError");
                
                $(".member-regist-wrapper").find("#password").after(passwordEqualErrorDom);
                $(".member-regist-wrapper").find("#confirmPassword").after(confirmPasswordEqualErrorDom);
            }
            else {
                $(".member-regist-wrapper").find("#password").closest("div").find(".passwordEqualError").remove();
                $(".member-regist-wrapper").find("#confirmPassword").closest("div").find(".passwordEqualError").remove();
            }
            
        });

        // 회원가입 이메일 중복 체크 이벤트
        // blur는 다른 것을 누를 때 반응함.
        $(".member-regist-wrapper").find("#email")
        .on("blur", function(){
           // 호이스팅 
           // 여기서 this는 callback함수를 가리킨다. function(ajaxResponse){ }
           var emailValue = $(this).val();
           // 이번에 this는 $()를 안붙였다!
           // 왜? $(".member-regist-wrapper").find("#email")를 가리켜야하기 때문이다.
           var that = this; // 그래서 밑에의 that에는 $()를 붙인다.
           
           // ajax
           // $.get("url", {파라미터}, function(콜백 함수){}); 
           $.get("/member/available", 
            {
              "email":emailValue
            }, 
           function(ajaxResponse){
            /* ajaxResponse
             {
                "status": 200,
                "data": {
                    "available": true
                }
            }
            */
           if(ajaxResponse === 200 &&
            ajaxResponse.data.available) {
                // available값이 true면 사용할 수 있는 이메일이다.
                $(that).closest("div").find(".emailDuplicateError").remove();
            } else { // 사용할 수 없는 이메일
                
                if($(that).closest("div").find(".emailDuplicateError").length > 0){
                
                var emailErrorDom = $("<div></div>");
                emailErrorDom.text("이미 사용중인 이메일입니다. 다른 이메일을 입력해주세요.");
                emailErrorDom.addClass("error");
                emailErrorDom.addClass("emailDuplicateError");
                    return;
                }
                
                // 이때 this -> $.get ()에 있는 email 자바스크립트의 this는 function을 호출한 this이다.
                // 이때 that은 input
                $(that).after(emailErrorDom);
            }
           }); 
        });
        
        $(".member-regist-wrapper").find("input")
        .on("change", function(){
            var hasErrors = $(".member-regist-wrapper").find(".error").length > 0;
			hasErrors = hasErrors || $("input:invalid").length > 0;
            
            if(hasErrors){
                $(".member-regist-wrapper").find(".regist-button").attr("disabled", "disabled");
            }
            else {
                $(".member-regist-wrapper").find(".regist-button").removeAttr("disabled"); 
            }
        });

});
