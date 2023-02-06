layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    /**
     * 用户列表展示
     */
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/user/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: "id", title: '编号', fixed: "true", width: 80},
            {field: 'userName', title: '用户名', minWidth: 50, align: "center"},
            {field: 'email', title: '用户邮箱', minWidth: 100, align: 'center'},
            {field: 'phone', title: '用户电话', minWidth: 100, align: 'center'},
            {field: 'trueName', title: '真实姓名', align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center', minWidth: 150},
            {field: 'updateDate', title: '更新时间', align: 'center', minWidth: 150},
            {title: '操作', minWidth: 150, templet: '#userListBar', fixed: "right", align: "center"}
        ]]
    });

    /*搜索操作*/
    $(".search_btn").click(function () {
        table.reload("userListTable", {
            page: {
                curr: 1
            },
            where: {
                "userName": $("input[name='userName']").val(),
                "email": $("input[name='email']").val(),
                "phone": $("input[name='phone']").val(),
            }
        })
    });

    /*绑定头部工具栏*/
    table.on("toolbar(users)", function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        console.log(checkStatus.data);
        if (obj.event === 'add') {
            //添加
            openAddOrUpdateUserDialog();
        } else if (obj.event === 'del') {
            //删除
            deleteUserByIds(checkStatus.data);
        }
    });


    function deleteUserByIds(datas) {
        if (datas.length == 0) {
            layer.msg("请选择删除数据?");
            return;
        }
        //收集数据
        var ids = [];

        //遍历
        for (var x in datas) {
            ids.push(datas[x].id);
        }
        console.log(ids + "<<<");
        layer.confirm("你确定要是删除数据吗?", {
            btn: ["确认", "取消"]
        }, function (index) {
            //关闭
            layer.close(index);
            //批量删除
            $.post(ctx + "/user/delete", {"ids": ids.toString()}, function (data) {
                if (data.code == 200) {
                    layer.msg("删除OK", {icon: 6});
                    //重载加载数据
                    tableIns.reload()
                } else {
                    //删除失败的提示信息
                    layer.msg(data.msg, {icon: 5});
                }
            }, "json");
        });
    }


    function openAddOrUpdateUserDialog(userId) {
        var title = "<h3>用户模块--添加</h3>";
        var url = ctx + "/user/addOrUpdatePage";

        //判断
        if (userId) {
            title = "<h3>用户模块--更新</h3>";
            url += "?id=" + userId;
        }

        //弹出iframe
        layui.layer.open({
            title: title,
            type: 2,
            content: url,
            area: ["650px", "400px"],
            maxmin: true
        })
    }

    /*绑定行内工具栏*/
    table.on("tool(users)", function (obj) {
        console.log(obj.data);
        if (obj.event == 'edit') {
            //编辑
            openAddOrUpdateUserDialog(obj.data.id);
        } else if (obj.event == 'del') {
            //删除
            deleteUserById(obj.data.id);
        }
    });

    //单独删除一条记录
    function deleteUserById(id) {
        layer.confirm("你确定要是删除数据吗?", {
            btn: ["确认", "取消"]
        }, function (index) {
            //关闭
            layer.close(index);
            //批量删除
            $.post(ctx + "/user/delete", {"ids": id}, function (data) {
                if (data.code == 200) {
                    layer.msg("删除OK", {icon: 6});
                    //重载加载数据
                    tableIns.reload()
                } else {
                    //删除失败的提示信息
                    layer.msg(data.msg, {icon: 5});
                }
            }, "json");
        });
    }
});