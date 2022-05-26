let index ={
		init:function(){
			$("#btn-save").on("click",()=>{ //function(){}, ()=>{} this를 바인딩 하기위해 function을 쓸꺼면  index 시작에 let_this = this;로 바인딩해서
				this.save();
			});//리스너를 만드는것
		
		},

	save:function(){
		//alert("user의 save함수 호출됨");
		let data={
				title:$("#title").val(),
				content:$("#content").val()
		};
			$.ajax({	
			type:"POST",
			url:"/api/board", 
			data:JSON.stringify(data), // http body데이터
			contentType:"application/json; charset=utf-8", //body가 어떤 데이터인지 알려주는 것 data json과 contentType "application/json; charset=utr-8"은 세트 
			dataType:"json" //요청을 서버로해서 요청이 왔을 때 기본적으로 응답은 String으로 오는데 (모습이 json ->js오브젝트로 변경한다.)
		}).done(function(resp){ // 회원 가입이 정상으로 되는 데이터 전송시 resp로 1이 리턴 
			//응답에 성공 (done을 실행)
			console.log(resp);
			alert("게시글이 저장되었습니다.");
			location.href="/";
		}).fail(function(error){
			//응답에 실패 (fail실행)
			alert(JSON.stringify(error)); //에러를 json으로 날라오게 한다.
		});//ajax통신을 이용해서 3개의 데이터를 json으로 변경해 insert 요청을 한다 
	
	},
	
}

index.init();