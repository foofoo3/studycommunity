
function comment2target(targetId,type,content){
    // 判断回复是否为空
    if (content === "" || content.length === 0 || !content){
        alert("回复不能为空");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response){
            if (response.code === 200){
                window.location.reload();
            }else {
                if (response.code === 2003){
                    alert(response.message);
                    // let conf = confirm(response.message);
                    // if (conf){
                    //     window.open("/login");
                        // window.localStorage.setItem("closable","true");
                    // }
                }else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

// 提交回复
function post(){
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    comment2target(questionId,1,content);
}


function comment(e){
    let commentId = e.getAttribute("data-id");
    let content = $('#input-'+ commentId).val();
    comment2target(commentId,2,content);
}

// 展开二级评论
function collapseComments(e){
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);
    //获取二级评论展开状态
    let collapse = e.getAttribute("collapse");
    if (collapse){
        //折叠二级评论
        comments.removeClass("in");
        //去除标记展开状态
        e.removeAttribute("collapse");
        e.classList.remove("active");
    }else {

        let subCommentContainer = $("#comment-" + id);

        $.getJSON("/comment/"+id, function(data) {
            if (subCommentContainer.children().length !== 1){
                comments.addClass("in");
                //标记展开状态
                e.setAttribute("collapse","in");
                e.classList.add("active");
            }else {
                $.each(data.data.reverse(), function(index,comment) {

                    let mediaLeftEl = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded comment-img",
                        "src":"https://tvax1.sinaimg.cn/thumbnail/007E7MVRly1h68twaikmyj30jt0juabj.jpg"
                    }));

                    let mediaBodyEl = $("<div/>",{
                        "class":"media-body comment-body",
                    }).append($("<h5/>",{
                        "class":"media-heading comment-font",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmt_create).format('YYYY-MM-DD')
                    })));

                    let mediaEl = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftEl).append(mediaBodyEl);


                    let commentEl = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaEl);

                    subCommentContainer.prepend(commentEl);
                });

                //展开二级评论
                comments.addClass("in");
                //标记展开状态
                e.setAttribute("collapse","in");
                e.classList.add("active");
            }
        });
    }
}

function selectTag(e){
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if (previous.indexOf(value) === -1){
        if (previous){
            $("#tag").val(previous + "，" + value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag(){
    $("#select-tag").show();
}

function noLogin(){
    alert("用户未登录，请登录后操作");
    return false;
}

function like(e){
    debugger
    let id = e.getAttribute("data-id");
    let uid = e.getAttribute("value");
    let like = $("#like-" + id);
    //如果显示评论未点赞
    if (like.hasClass("glyphicon-heart-empty")){
        $.ajax({
            type: "POST",
            url: "/commentLike",
            contentType:"application/json",
            data: JSON.stringify({
                "uid":uid,
                "target_id":id
            }),
            success: function (response){
                if (response.code === 200){
                    window.location.reload();
                }else {
                    if (response.code === 5200){
                        alert(response.message);
                    }else {
                        alert(response.message);
                    }
                }
                console.log(response);
            },
            dataType: "json"
        });
        //如果显示评论已点赞
    }else if (like.hasClass("glyphicon-heart")) {
        $.ajax({
            type: "POST",
            url: "/commentLikeReduce",
            contentType: "application/json",
            data: JSON.stringify({
                "uid": uid,
                "target_id": id
            }),
            success: function (response) {
                if (response.code === 200) {
                    window.location.reload();
                } else {
                    if (response.code === 5201) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            },
            dataType: "json"
        });
    //    判断是否为问题点赞
    }else if(like.hasClass("glyphicon-ok")){
        //问题已被点赞
        if (like.hasClass("question-liked")){
            $.ajax({
                type: "POST",
                url: "/questionLikeReduce",
                contentType: "application/json",
                data: JSON.stringify({
                    "uid": uid,
                    "target_id": id
                }),
                success: function (response) {
                    if (response.code === 200) {
                        window.location.reload();
                    } else {
                        if (response.code === 5201) {
                            alert(response.message);
                        } else {
                            alert(response.message);
                        }
                    }
                    console.log(response);
                },
                dataType: "json"
            });
        //   问题未被点赞
        }else {
            $.ajax({
                type: "POST",
                url: "/questionLike",
                contentType: "application/json",
                data: JSON.stringify({
                    "uid": uid,
                    "target_id": id
                }),
                success: function (response) {
                    if (response.code === 200) {
                        window.location.reload();
                    } else {
                        if (response.code === 5200) {
                            alert(response.message);
                        } else {
                            alert(response.message);
                        }
                    }
                    console.log(response);
                },
                dataType: "json"
            });
        }
    }
}

function star(e){
    debugger
    let id = e.getAttribute("data-id");
    let uid = e.getAttribute("value");
    let star = $("#star-" + id);
    //如果显示问题未收藏
    if (star.hasClass("glyphicon-star-empty")){
        $.ajax({
            type: "POST",
            url: "/questionStar",
            contentType:"application/json",
            data: JSON.stringify({
                "uid":uid,
                "target_id":id
            }),
            success: function (response){
                if (response.code === 200){
                    window.location.reload();
                }else {
                    if (response.code === 5200){
                        alert(response.message);
                    }else {
                        alert(response.message);
                    }
                }
                console.log(response);
            },
            dataType: "json"
        });
        //如果显示问题已收藏
    }else if (star.hasClass("glyphicon-star")) {
        $.ajax({
            type: "POST",
            url: "/questionStarCancel",
            contentType: "application/json",
            data: JSON.stringify({
                "uid": uid,
                "target_id": id
            }),
            success: function (response) {
                if (response.code === 200) {
                    window.location.reload();
                } else {
                    if (response.code === 5201) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            },
            dataType: "json"
        });
    }
}

function commentTime(e){
    let id = e.getAttribute('value');
    location.href=("/question/"+id);
}

function commentLike(e){
    let id = e.getAttribute('value');
    location.href=("/question/"+id+"?like=1");
}

function questionsort(e){
    let type = e.getAttribute("type");
    let search = e.getAttribute("search");
    let tag = e.getAttribute("tag");
    if (search == null && tag == null){
        location.href=("/?type="+type);
    }else if (search != null && tag == null){
        location.href=("/?type="+type+"&search="+search);
    }else if (search == null && tag != null){
        location.href=("/?type="+type+"&tag="+tag);
    }else if (search != null && tag != null){
        location.href=("/?type="+type+"&search="+search+"&tag="+tag);
    }else {
        location.href=("/?type="+type);
    }
}

