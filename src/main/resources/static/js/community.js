
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
    debugger
    let id = e.getAttribute("data-id");
    let uid = e.getAttribute("user");
    let adminId = e.getAttribute("admin");
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
                        "class":"media-left",
                    }).append($("<a/>",{
                        "href" : '/information/' + comment.user.uid,
                        "class":"pointer"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded comment-img",
                        "src":comment.user.face
                    })));


                    let mediaBodyEl;
                    if(comment.user.uid == uid || adminId != null){
                        mediaBodyEl = $("<div/>", {
                            "class": "media-body comment-body",
                        }).append($("<h5/>", {
                            "class": "media-heading comment-font",
                            "html": comment.user.name
                        })).append($("<div/>", {
                            "html": comment.content
                        })).append($("<div/>", {
                            "class": "menu"
                        }).append($("<a/>",{
                            "class": "community-menu pointer",
                            "style": "margin-left:10px",
                            "onclick":"trashSecondComment(" + comment.id + ")",
                            "html": "&nbsp;删除二级评论"
                        })).append($("<span/>", {
                            "class": "pull-right",
                            "html": moment(comment.gmt_create).format('YYYY-MM-DD')
                        })));
                    }else {
                        mediaBodyEl = $("<div/>", {
                            "class": "media-body comment-body",
                        }).append($("<h5/>", {
                            "class": "media-heading comment-font",
                            "html": comment.user.name
                        })).append($("<div/>", {
                            "html": comment.content
                        })).append($("<div/>", {
                            "class": "menu"
                        }).append($("<span/>", {
                            "class": "pull-right",
                            "html": moment(comment.gmt_create).format('YYYY-MM-DD')
                        })));
                    }


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

function trashQuestion(e,type){
    let id = e.getAttribute('value');
    let res = confirm('确定删除此问题吗？');
    if (res){
        $.ajax({
            url: "/deleteQuestion/"+id,
            type: "post",
            success: function(response){
                if (response.code === 200){
                    if (type === 'q'){
                        alert("删除成功")
                        window.location.replace("/");
                    }else if (type === 'p'){
                        alert("删除成功")
                        window.location.reload();
                    }
                }else {
                    if (response.code === 6000) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
            }
        });


    }
}

function trashStar(e){
    let id = e.getAttribute('value');
    let userId = e.getAttribute('user');
    let res = confirm('确定删除此收藏吗？');
    if (res) {
        $.ajax({
            type: "POST",
            url: "/questionStarCancel",
            contentType: "application/json",
            data: JSON.stringify({
                "uid": userId,
                "target_id": id
            }),
            success: function (response) {
                if (response.code === 200) {
                    alert("删除成功")
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

function trashComment(e){
    let id = e.getAttribute('value');
    let res = confirm('确定删除此评论吗？');
    if (res) {
        $.ajax({
            type: "POST",
            url: "/deleteComment/" + id,
            success: function (response) {
                if (response.code === 200) {
                    alert("删除成功")
                    window.location.reload();
                } else {
                    if (response.code === 5201) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            }
        });
    }
}

function trashSecondComment(id){
    let res = confirm('确定删除此二级评论吗？');
    if (res) {
        $.ajax({
            type: "POST",
            url: "/deleteComment/" + id,
            success: function (response) {
                if (response.code === 200) {
                    alert("删除成功")
                    window.location.reload();
                } else {
                    if (response.code === 5201) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            }
        });
    }
}

function trashNotification(e){
    let id = e.getAttribute('value');
    let res = confirm('确定删除此条通知吗？');
    if (res) {
        $.ajax({
            type: "POST",
            url: "/deleteNotification/" + id,
            success: function (response) {
                if (response.code === 200) {
                    alert("删除成功")
                    window.location.reload();
                } else {
                    if (response.code === 5201) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            }
        });
    }
}

function ban(e){
    let uid = e.getAttribute('value');
    let res = confirm('确定封禁此用户吗？');
    if (res) {
        $.ajax({
            type: "POST",
            url: "/ban/" + uid,
            success: function (response) {
                if (response.code === 200) {
                    alert("封禁成功")
                    window.location.reload();
                } else {
                    if (response.code === 9000) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            }
        });
    }
}

function unban(e){
    let uid = e.getAttribute('value');
    let res = confirm('确定解封此用户吗？');
    if (res) {
        $.ajax({
            type: "POST",
            url: "/unban/" + uid,
            success: function (response) {
                if (response.code === 200) {
                    alert("解封成功")
                    window.location.reload();
                } else {
                    if (response.code === 9001) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            }
        });
    }
}

function cancellation(e){
    let uid = e.getAttribute('value');
    let res = confirm('确定删除此用户吗？');
    if (res) {
        $.ajax({
            type: "POST",
            url: "/cancellation/" + uid,
            success: function (response) {
                if (response.code === 200) {
                    alert("删除用户成功")
                    window.location.reload();
                } else {
                    if (response.code === 9002) {
                        alert(response.message);
                    } else {
                        alert(response.message);
                    }
                }
                console.log(response);
            }
        });
    }
}

function modifyFace() {
    document.querySelector('#face').hidden = "";
}

function modifyName() {
    document.querySelector('#name').type = "text";
    document.querySelector('#submit').hidden = "";
}

function modifyDescription() {
    document.querySelector('#description').hidden = "";
    document.querySelector('#submit').hidden = "";
}

function modifyPassword() {
    document.querySelector('#password').hidden = ""
    document.querySelector('#submit').hidden = "";
}

function submit(){
    var form = document.getElementById("form")
    form.submit();
}

function modifyAnnouncement() {
    document.querySelector('#announcement').hidden = "";
    document.querySelector('#submit').hidden = "";
}