// 提交恢复
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

// 展开二级评论
function collapseComments(e){
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id)
    //获取二级评论展开状态
    let collapse = e.getAttribute("collapse")
    if (collapse){
        //折叠二级评论
        comments.removeClass("in")
        //去除标记展开状态
        e.removeAttribute("collapse")
        e.classList.remove("active")
    }else {
        //展开二级评论
        comments.addClass("in")
        //标记展开状态
        e.setAttribute("collapse","in")
        e.classList.add("active")
    }

}