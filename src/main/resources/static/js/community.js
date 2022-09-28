function post(){
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    // 判断回复是否为空
    if (content === "" || content.length === 0 || !content){
        alert("回复不能为空")
        return false;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response){
            if (response.code === 200){
                window.location.reload()
            }else {
                if (response.code === 2003){
                    let conf = confirm(response.message);
                    if (conf){
                        window.open("/login")
                        // window.localStorage.setItem("closable","true");
                    }
                }else {
                    alert(response.message)
                }
            }
            console.log(response)
        },
        dataType: "json"
    });
}