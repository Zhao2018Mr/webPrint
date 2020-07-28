<template>
  <div id="nr">
        <div style="font-size: 20px;width:13cm; margin-top: 0.5cm;border: 1px solid #000000; padding: 10px;height: 14cm ">
          <div style="">
            <div style="margin-top: 10px">姓&nbsp;&nbsp;名:<span style="font-weight: 500;font-size: 20px"></span>
            </div>
            <div style="margin-top: 10px">地&nbsp;&nbsp;址:
            </div>
            <div style="font-size: 22px;padding:15px 5px 15px 0">上海市浦东新区xxxxxxxxxxxxx</div>
            <div>电话:</div>
            <div><span style="font-size: 22px;font-weight: bold;padding:15px 0">1234567897</span></div>
            <table>
              <tr style="height: 24px;line-height: 24px">
                <td style="">类型:</td>
                <td></td>
                <td style="margin-left: 20px">贴数:</td>
                <td style="font-size: 24px;font-weight: bold">14</td>
              </tr>
            </table>
          </div>
        </div>
    <button @click="send"></button>
    </div>
</template>
<script>
  export default {
    data() {
      return {
        // 9 用于id 校验类似的
        path: "ws://localhost:22599/webSocket/9",
        socket: "",
      }
    },
    mounted() {
      // 初始化
      this.init()
    },
    created() {
    },
    methods: {
      init: function () {
        if (typeof (WebSocket) === "undefined") {
          alert("您的浏览器不支持socket")
        } else {
          // 实例化socket
          this.socket = new WebSocket(this.path)
          // 监听socket连接
          this.socket.onopen = this.open
          // 监听socket错误信息
          this.socket.onerror = this.error
          // 监听socket消息
          this.socket.onmessage = this.getMessage
        }
      },
      open: function () {
        console.log("socket连接成功")
      },
      error: function () {
        alert("连接打印插件错误,请检查打印服务是否启动或刷新此页面")
        this.$message({
          message: "连接错误,请检查打印服务是否启动或刷新此页面",
          type: 'error',
        })
      },
      getMessage: function (msg) {
        if(msg.data == '打印成功'){
          this.$message({
            message: "打印成功",
            type: 'success',
          })
        }
      },
      send: function () {
          this.socket.send(document.getElementById("kd").innerHTML)

      },
      close: function () {
        console.log("socket已经关闭")
      }
    },
    destroyed() {
      // 销毁监听
      this.socket.onclose = this.close
    }
  }
</script>
