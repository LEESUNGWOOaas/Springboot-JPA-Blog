let index ={
		init:function(){
			$("#btn-save").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기위해 function을 쓸꺼면  index 시작에 let_this = this;로 바인딩해서
				this.save();
			});//리스너를 만드는것
			
			/*$("#btn-login").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기위해 function을 쓸꺼면  index 시작에 let_this = this;로 바인딩해서
				this.login();
			});*/
		},

	save:function(){
		//alert("user의 save함수 호출됨");
		let data={
				username:$("#username").val(),
				password:$("#password").val(),
				email:$("#email").val()
		};
		//console.log(data);
		//ajax가 통신을 성공하고 json을 리턴하면 서버가 자동으로 자바오브젝트로 변환해줌
		//ajax호출시 default가 비동기 호출  (밑에 코드가 있을때 같이 실행함 그러다 ajax의 응답에 결과가 오면 하던 실행위에 ajax코드값이 실행된다 ) 
		$.ajax({
			//통신 (회원가입요청)
			type:"POST",
			url:"/auth/joinProc", // join은 생략  post인것과 user에 값을 담을꺼기때문에 안적어도된다.
			data:JSON.stringify(data), // http body데이터
			contentType:"application/json; charset=utf-8", //body가 어떤 데이터인지 알려주는 것 data json과 contentType "application/json; charset=utr-8"은 세트 
			dataType:"json" //요청을 서버로해서 요청이 왔을 때 기본적으로 응답은 String으로 오는데 (모습이 json ->js오브젝트로 변경한다.)
		}).done(function(resp){ // 회원 가입이 정상으로 되는 데이터 전송시 resp로 1이 리턴 
			//응답에 성공 (done을 실행)
			console.log(resp);
			alert("회원가입이 되었습니다.");
			location.href="/";
		}).fail(function(error){
			//응답에 실패 (fail실행)
			alert(JSON.stringify(error)); //에러를 json으로 날라오게 한다.
		});//ajax통신을 이용해서 3개의 데이터를 json으로 변경해 insert 요청을 한다 
	
	},//login
	/*login:function(){
		//alert("user의 save함수 호출됨");
		let data={
				username:$("#username").val(),
				password:$("#password").val(),
		};
		
		$.ajax({
			//통신 (회원가입요청)
			type:"POST",
			url:"/api/user/login", // join은 생략  post인것과 user에 값을 담을꺼기때문에 안적어도된다.
			data:JSON.stringify(data), // http body데이터
			contentType:"application/json; charset=utf-8", //body가 어떤 데이터인지 알려주는 것 data json과 contentType "application/json; charset=utr-8"은 세트 
			dataType:"json" //요청을 서버로해서 요청이 왔을 때 기본적으로 응답은 String으로 오는데 (모습이 json ->js오브젝트로 변경한다.)
		}).done(function(resp){ // 회원 가입이 정상으로 되는 데이터 전송시 resp로 1이 리턴 
			//응답에 성공 (done을 실행)
			console.log(resp);
			alert("로그인이 완료 되었습니다.");
			location.href="/";
		}).fail(function(error){
			//응답에 실패 (fail실행)
			alert(JSON.stringify(error)); //에러를 json으로 날라오게 한다.
		});//ajax통신을 이용해서 3개의 데이터를 json으로 변경해 insert 요청을 한다 
	}
	*/
	
}

index.init();