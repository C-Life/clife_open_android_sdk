#Android接入指南

注：本文为C-Life Android终端开发工具的新手使用教程，只涉及教授SDK的使用方法，默认读者已经熟悉IDE的基本使用方法（本文以Android Studio为例），以及具有一定的编程知识基础等。

##1. 向C-Life注册你的应用程序
请到 应用中心 页面创建移动应用，填写资料后，将获得AppID和AppSecret，可立即用于开发。但应用登记完成后还需要提交审核，只有审核通过的应用才能正式发布使用。

##2. 下载C-Life终端DEMO
请前往[下载中心](https://github.com/C-Life/clife_open_android_sdk)下载最新SDK包。

##3. 搭建开发环境
###3.1在Android Studio中建立你的工程。
###3.2在工程的build文件中


    allprojects {
        repositories {
            jcenter()
            //和而泰对外仓库
            maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
        }
    }

    dependencies {
        //和而泰sdk库
         compile 'com.github.szhittech:HetOpenSdk:1.0.5-SNAPSHOT'
    }
	
  
  

###3.3添加权限
   需在AndroidManifest.xml中声明以下权限
   
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.READ_PHONE_STATE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
	<uses-permission android:name="android.permission.BLUETOOTH" />
	<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
		
	
##4. 功能说明

###4.1初始化SDK

 使用CLife SDK，需要先调用sdk初始化方法，建议在Application中调用

**接口调用请求说明**

	HetSdk.getInstance().init（）

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>
		<tr>
			<td>appId</td>
			<td>是</td>
			<td>string</td>
			<td>应用标识</td>
		</tr>
		<tr>
			<td>context</td>
			<td>是</td>
			<td>Context</td>
			<td>上下文常量</td>
		</tr>
		<tr>
			<td>secret </td>
			<td>是</td>
			<td>string</td>
			<td>用户密匙</td>
		</tr>
		<tr>
			<td>configModel</td>
			<td>是</td>
			<td>ConfigModel</td>
			<td>初始配置</td>
		</tr>
	</tbody>
</table>

**configModel说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">字段名称</th>
			<th width="11%">字段类型</th>
			<th width="74%">字段说明</th>
		</tr>
		<tr>
			<td>isOpenWifiContorl</td>
			<td>boolean</td>
			<td>是否开启wifi功能</td>
		</tr>
	    <tr>
			<td>isOpenBleControl</td>
			<td>boolean</td>
			<td>是否开启蓝牙功能</td>
		</tr>
	    <tr>
			<td>isLog</td>
			<td>boolean</td>
			<td>是否开启调试信息</td>
		</tr>
		<tr>
			<td>isOpenPost</td>
			<td>boolean</td>
			<td>是否为开放平台端口（现在都设为false）</td>
		</tr>
		<tr>
			<td>host</td>
			<td>int</td>
			<td>网络环境设置: 0x01：正式 0x02：预发布 0x03:内网 0x04：测试环境</td>
		</tr>

	</tbody>
</table>


	
    
###4.2 授权登录

   使用auth登录的方式登录clife开放平台，只有登录后，用户才可以使用相关api绑定，控制，管理设备
   
   授权登录界面如下图
       授权登录，用户输入账号密码登录
       若用户没有clife账号，需要先注册clife账号
       若用户忘记密码，可以使用找回密码功能找回密码

   ![](http://i.imgur.com/bO48qSQ.jpg)
     
                 
###4.3 获取用户信息


  获取授权登录用户的用户信息



**接口调用请求说明**

	HetUserApi.getInstance().getUserMess()

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回用户的json信息</td>
		</tr>
	</tbody>
</table>
**返回json信息说明**

<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">字段名称</th>
			<th width="11%">字段类型</th>
			<th width="74%">字段说明</th>
		</tr>
		<tr>
			<td>userName</td>
			<td>string</td>
			<td>用户名称</td>
		</tr>
		<tr>
			<td>phone</td>
			<td>string</td>
			<td>用户手机</td>
		</tr>
		<tr>
			<td>email</td>
			<td>string</td>
			<td>用户邮箱</td>
		</tr>
		<tr>
			<td>sex</td>
			<td>number</td>
			<td>性别（1-男，2-女）</td>
		</tr>
		<tr>
			<td>birthday</td>
			<td>string</td>
			<td>生日（yyyy-MM-dd）</td>
		</tr>
		<tr>
			<td>weight</td>
			<td>number</td>
			<td>体重（克）</td>
		</tr>
		<tr>
			<td>height</td>
			<td>number</td>
			<td>身高（厘米）</td>
		</tr>
		<tr>
			<td>avatar</td>
			<td>string</td>
			<td>头像URL</td>
		</tr>
		<tr>
			<td>city</td>
			<td>string</td>
			<td>城市名</td>
		</tr>
	</tbody>
</table>

###4.4 退出授权登录

	HetUserApi.getInstance().logout();

##5 设备绑定管理

   > 目前，支持蓝牙设备，wifi设备绑定

###5.1 设备model说明

  本文档中选有的devicemodel，参数说明

**设备devicemodel说明**

<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">字段名称</th>
			<th width="11%">字段类型</th>
			<th width="74%">字段说明</th>
		</tr>
		<tr>
			<td>deviceId</td>
			<td>string</td>
			<td>设备标识</td>
		</tr>
		<tr>
			<td>macAddress</td>
			<td>string</td>
			<td>MAC地址</td>
		</tr>
		<tr>
			<td>deviceBrandId</td>
			<td>number</td>
			<td>设备品牌标识</td>
		</tr>
		<tr>
			<td>deviceBrandName</td>
			<td>string</td>
			<td>设备品牌名称</td>
		</tr>
		<tr>
			<td>deviceTypeId</td>
			<td>number</td>
			<td>设备大分类标识</td>
		</tr>
		<tr>
			<td>deviceTypeName</td>
			<td>string</td>
			<td>设备大分类名称</td>
		</tr>
		<tr>
			<td>deviceSubtypeId</td>
			<td>number</td>
			<td>设备子分标识</td>
		</tr>
		<tr>
			<td>deviceSubtypeName</td>
			<td>string</td>
			<td>设备子分类名称</td>
		</tr>
		<tr>
			<td>deviceName</td>
			<td>string</td>
			<td>设备名称</td>
		</tr>
		<tr>
			<td>roomId</td>
			<td>string</td>
			<td>房间标识</td>
		</tr>
		<tr>
			<td>roomName</td>
			<td>string</td>
			<td>房间名称</td>
		</tr>
		<tr>
			<td>authUserId</td>
			<td>string</td>
			<td>授权设备用户标识</td>
		</tr>
		<tr>
			<td>bindTime</td>
			<td>string</td>
			<td>绑定时间</td>
		</tr>
		<tr>
			<td>onlineStatus</td>
			<td>number</td>
			<td>在线状态（1-正常，2-异常）</td>
		</tr>
		<tr>
			<td>share</td>
			<td>number</td>
			<td>设备分享（1-是，2-否，3-扫描分享）<font color=#c00>【2015-11-11新增状态（3）】</font></td>
		</tr>
		<tr>
			<td>controlType</td>
			<td>number</td>
			<td>控制类型（1-原生，2-插件，3-H5插件）</td>
		</tr>
		<tr>
			<td>userKey</td>
			<td>string</td>
			<td>MAC与设备ID生成的KEY</td>
		</tr>
		<tr>
			<td>productId</td>
			<td>number</td>
			<td>设备型号标识</td>
		</tr>
		<tr>
			<td>productName</td>
			<td>string</td>
			<td>设备型号名称</td>
		</tr>
		<tr>
			<td>productCode</td>
			<td>string</td>
			<td>设备型号编码</td>
		</tr>
		<tr>
			<td>productIcon</td>
			<td>string</td>
			<td>设备型号图标</td>
		</tr>
		<tr>
			<td>moduleId</td>
			<td>number</td>
			<td>模块标识</td>
		</tr>
		<tr>
			<td>moduleType</td>
			<td>number</td>
			<td>模块类型（1-WiFi，2-蓝牙，3-音频，4-GSM，5-红外）</td>
		</tr>
		<tr>
			<td>moduleName</td>
			<td>string</td>
			<td>模块名称</td>
		</tr>
		<tr>
			<td>radiocastName</td>
			<td>string</td>
			<td>设备广播名</td>
		</tr>
		<tr>
			<td>deviceCode</td>
			<td>string</td>
			<td><font color=#c00>【2016-06-03新增】</font>设备编码</td>
		</tr>
	</tbody>
</table>


###5.2 wifi设备绑定

   请确任在初始化sdk的配置时已开启wifi功能


**wifi设备绑定**

	HetWifiBindApi getInstance().startBind()

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        <tr>
			<td>wifiPassword</td>
			<td>是</td>
			<td>string</td>
			<td>手机当前连接wifi的wifi密码，请确保密码输入正确</td>
		</tr>

        <tr>
			<td>productId</td>
			<td>是</td>
			<td>string</td>
			<td>设备的产品id，从开放平台获取</td>
		</tr>

         <tr>
			<td>iWifiBind</td>
			<td>是</td>
			<td>IWifiBind</td>
			<td>wifi扫描绑定回调，参考说明</td>
		</tr>


	</tbody>
</table>

**接口iWifiBind说明**

		 /**
	     * 绑定状态
	     * @param hetWifiBindState  绑定状态
	     * 0 初始 1 开始扫描 2 扫描到设备 3 提交设备信息成功
	     * 4 开始绑定 5 绑定成功 6 异常
	     * @param msg     绑定状态描述
	     */
	    public void onStatus(HetWifiBindState hetWifiBindState, String msg);

	    /**
	     * 绑定失败
	     * @param errId  绑定失败代码
	     *  10001 未扫描到任何设备
	     *  10002 绑定设备失败
	     * @param msg    绑定失败描述
	     */
	    public void onFailed(int errId,String msg);

	    /**
	     * 绑定成功
	     * @param deviceModel 绑定成功的设备model
	     */
	    public void onSuccess(DeviceModel deviceModel);


	    /**
	     * 扫描进度
	     * @param type  类型
	     * @param value 扫描设备进度值
	     */
	    public void onProgress(int type,int value);


###5.3 蓝牙设备绑定

   请确任在初始化sdk的配置时已开启蓝牙功能


**5.3.1 初始化蓝牙绑定**

	HetBleBindApi.getInstance().init()

**5.3.2 开始蓝牙绑定**

	HetBleBindApi.getInstance().startBind()

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>


        <tr>
			<td>productId</td>
			<td>是</td>
			<td>string</td>
			<td>设备的产品id，从开放平台获取</td>
		</tr>

         <tr>
			<td>iBleBind</td>
			<td>是</td>
			<td>IBleBind</td>
			<td>蓝牙扫描绑定回调，参考说明</td>
		</tr>


	</tbody>
</table>

**接口IBleBind说明**

	      /**
	     * 绑定状态
	     *
	     * @param devices 扫描到设备(多个)
	     * @param msg      扫描绑定状态描述
	     */
	    public void onScanDevices(String devices, String msg);

	    /**
	     * 绑定失败
	     *
	     * @param errId 绑定失败代码
	     * @param msg   绑定失败描述
	     */
	    public void onFailed(int errId, String msg);

	    /**
	     * 绑定成功
	     *
	     * @param deviceModel 绑定成功的设备model
	     */
	    public void onSuccess(DeviceModel deviceModel);


**5.3.3 连接蓝牙设备**

 蓝牙可能同时扫描到多个蓝牙设备，选择指定设备连接

	HetBleBindApi.getInstance().connect()


**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

	    <tr>
			<td>deviceModel</td>
			<td>是</td>
			<td>DeviceModel</td>
			<td>扫描到的蓝牙设备</td>
		</tr>
	</tbody>
</table>


###5.4 获取绑定设备列表

   绑定成功后，用户可以获取绑定成功的设备列表，获取到设备列表，才可以控制设备

**接口调用请求说明**

	HetDeviceListApi.getInstance().getBindList()

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

         <tr>
			<td>deviceModel</td>
			<td>是</td>
			<td>DeviceModel</td>
			<td>扫描到的蓝牙设备</td>
		</tr>

		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回设备model的json信息（列表）</td>
		</tr>

           <tr>
			<td>deviceName</td>
			<td>是</td>
			<td>string</td>
			<td>修改后的设备名称</td>
		</tr>
	</tbody>
</table>

**返回json信息说明**



###5.5 修改设备信息

   修改设备信息，用户可以修改设备的名称，使用HetDeviceManagerApi

   **接口调用请求说明**

	HetDeviceManagerApi.getInstance().update();

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回0</td>
		</tr>
	</tbody>
</table>

###5.6 解绑设备

   解除已绑定设备和CLife平台的绑定关系

**接口调用请求说明**

	HetDeviceManagerApi.getInstance().unBind();

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

	    <tr>
			<td>deviceModel</td>
			<td>是</td>
			<td>DeviceModel</td>
			<td>已绑定设备model</td>
		</tr>

		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回0</td>
		</tr>
	</tbody>
</table>


###5.7 获取设备协议

   获取设备在clife平台编辑的协议，在demo里面，进行设备控制的时候也会先获取设备的协议使用HetProtocolApi


	     /***
	     * 获取协议
	     * @param iHetCallback  回调
	     * @param productId 产品id
	     * @param type 协议类型 0或者不传-完整协议	，包括以下协议内容 1-设备基本信息
	     * 2-控制数据 3-运行数据 4-故障数据
	     */
	   	  public  void getProtocol(final IHetCallback iHetCallback,int productId,int type){

##6 WIFI设备控制

   用户需要根据在clife平台上注册的设备协议，定义model,后面的sdk版本会自动从后台拉取协议解析。
目前demo 里定义了香薰机的model，请参考定义需要使用的model

###6.1 控制wifi设备api

  指用户直接调用相关api请求clife平台控制wifi设备，使用HetDeviceWifiControlApi




- 向设备下发控制数据

   **接口调用请求说明**

	HetDeviceWifiControlApi.getInstance().setDataToDevice();

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        
		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回0</td>
		</tr>

	    <tr>
			<td>deviceId</td>
			<td>是</td>
			<td>String</td>
			<td>已绑定设备id</td>
		</tr>

        <tr>
			<td>json</td>
			<td>是</td>
			<td>String</td>
			<td>下发设备的控制数据</td>
		</tr>

	</tbody>
</table>


- 获取设备运行数据


**接口调用请求说明**

	HetDeviceWifiControlApi.getInstance().getRunFromDevice();

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        
		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回0</td>
		</tr>

	    <tr>
			<td>deviceId</td>
			<td>是</td>
			<td>String</td>
			<td>已绑定设备id</td>
		</tr>

	</tbody>
</table>


- 获取设备控制数据

**接口调用请求说明**

	HetDeviceWifiControlApi.getInstance().getConfigFromDevice();

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        
		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回0</td>
		</tr>

	    <tr>
			<td>deviceId</td>
			<td>是</td>
			<td>String</td>
			<td>已绑定设备id</td>
		</tr>

	</tbody>
</table>

- 获取设备异常数据

**接口调用请求说明**

	HetDeviceWifiControlApi.getInstance().getErrorDataFromDevice();

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        
		<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回0</td>
		</tr>

	    <tr>
			<td>deviceId</td>
			<td>是</td>
			<td>String</td>
			<td>已绑定设备id</td>
		</tr>

	</tbody>
</table>

###6.2 获取数据

   获取数据，指获取设备的运行数据和配置数据，使用HetDeviceSubmitApi方法

   [1] 初始化，数据回调接口 IDeviceUpdateView


	      /**
	      * 初始化设备请求数据
	      * @param deviceModel  设备model(必须获取deviceid后)
	      * @param deviceUpdateView  获取数据接口回调
	      */
          public void init(DeviceModel deviceModel,IDeviceUpdateView deviceUpdateView) {



   若用户未配置协议，需继承小循环model解析接口 IUdpModelParser，并在初始化的时候传递，用户需根据设备类型解析字节数据


		/**
		* 初始化设备请求数据
		* @param deviceModel  设备model
		* @param parse        小循环数据解析
		* @param deviceUpdateView  获取数据接口回调
		*/
		public void init(DeviceModel deviceModel, IUdpModelParser parse, IDeviceUpdateView deviceUpdateView) {


   [2] 开始刷新数据，指开始获取设备的数据，默认每5s请求一次，用户可以设置时间间隔


		/**
		* 开始请求数据
		*/
		public void start() {


   [3] 停止请求数据


		/**
		* 停止请求数据
		*/
	    public void stop() {


   [4] 释放资源


		/**
		* 释放请求资源
		*/
		public void destory() {


###6.3 下发数据

   下发数据指，发送相关命令操作设备，发送调用HetDeviceSubmitApi方法

   [1] 关于updateflag
      这个修改标记位是为了做统计和配置下发的时候设备执行相应的功能。下发数据必须传递updateflag标志


       例如，空气净化器（广磊K180）配置信息协议：

       紫外线(1)、负离子(2)、臭氧(3)、儿童锁(4)、开关(5)、WiFi(6)、过滤网(7)、模式(8)、定时(9)、风量(10)
       上面一共上10个功能，那么updateFlag就2个字节，没超过8个功能为1个字节，超过8个为2个字节，超过16个为3个字节，以此类推。

       打开负离子，2个字节，每一个bit的值为下：

       0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
   [2] 初始化


	   	/**
	   	* 初始化
	   	* @param deviceModel  设备model
	   	* @param submitUpdateView  获取数据接口回调
	   	*/
	   	public void init(DeviceModel deviceModel,ISubmitUpdateView submitUpdateView) {


   若用户未配置协议，需继承小循环model解析接口 IUdpModelParser，并在初始化的时候传递，用户需根据设备类型解析字节数据


		/**
		* 初始化
		* @param deviceModel  设备model
		* @param submitUpdateView  获取数据接口回调
		*/
	    public void init(DeviceModel deviceModel,ISubmitUpdateView submitUpdateView) {


   [3] 开启下发数据


		/**
		* 开启下发线程
		*/
	    public void start() {


   [4] 停止下发数据


		/**
		* 停止下发数据
		*/
	    public void stop() {


   [5] 释放资源


		/**
		* 释放相关资源
		*/
	    public void destory() {




###6.4 获取设备历史数据



   获取设备历史数据，分为运行数据，控制数据，异常数据，最多获取7天的历史数据使用HetDeviceDataHistroyApi

   [1] 运行数据


	     /**
	     * 获取设备运行数据列表（七天之内）
	     *
	     * @param iHetCallback
	     * @param deviceId	是	string	设备标识
	     * @param startDate	是	Date	开始时间
	     * @param endDate	否	Date	结束时间（默认为当天）
	     * @param pageRows	否	int	每页显示的行数，默认为20
	     * @param pageIndex	否	int	当前页，默认为1
	     */
	    public void getRunDataList(final IHetCallback iHetCallback, String deviceId, String startDate,String  endDate,int pageRows, int pageIndex) {


   [2] 控制数据

		/**
		* 获取设备控制数据列表（七天之内）
		*
		* @param iHetCallback
		* @param deviceId	是	string	设备标识
		* @param startDate	是	Date	开始时间
		* @param endDate	否	Date	结束时间（默认为当天）
		* @param pageRows	否	int	每页显示的行数，默认为20
		* @param pageIndex	否	int	当前页，默认为1
		*/
		public void getConfigDataList(final IHetCallback iHetCallback,String deviceId, String startDate,String  endDate,int pageRows, int pageIndex) {


   [3] 异常数据


	     /**
	     * 获取设备故障数据列表（七天之内）
	     *
	     * @param iHetCallback
	     * @param deviceId	是	string	设备标识
	     * @param startDate	是	Date	开始时间
	     * @param endDate	否	Date	结束时间（默认为当天）
	     * @param pageRows	否	int	每页显示的行数，默认为20
	     * @param pageIndex	否	int	当前页，默认为1
	     */
         public void getErrorDataList(final IHetCallback iHetCallback, String deviceId, String startDate,String  endDate,int pageRows, int pageIndex) {

##7 第三方wifi设备接入

   >第三方wifi设备接入指用户采用android系统的设备自带wifi模块，无法通过扫描方式接入路由器，直接绑定到clife后台。

###第三方wifi设备接入

  **第三方wifi设备绑定**

	HetThridWifiApi.getInstance().bindToHetServer

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        <tr>
			<td>mac</td>
			<td>是</td>
			<td>string</td>
			<td>设备的mac地址</td>
		</tr>

        <tr>
			<td>productId</td>
			<td>是</td>
			<td>string</td>
			<td>设备的产品id，从开放平台获取</td>
		</tr>

    	<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回设备model</td>
		</tr>


	</tbody>
</table>
  `
成功返回设备的model
设备控制请参考**6wifi设备控制**


##8 第三方蓝牙设备接入

   >第三方蓝牙设备接入指用户已开发好设备的的控制等功能，将设备接入clife开放平台，上传数据到clife平台，从clife平台获取  数据，蓝牙接入调用HetThridBleApi，需授权登录成功后才能使用相关api

###8.1提交设备信息到服务器

   将设备信息提交到服务器，设备信息可以通过大小分类获取，也可以默认传入，DEMO里面常用直接传入的形式
   提交成功后，服务器会为该设备返回deviceId，只有获取deviceId后才能上传数据和获取历史数据
**设备绑定**

	HetThridBleApi getInstance().bindToHetServer()

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>


        <tr>
			<td>deviceModel</td>
			<td>是</td>
			<td>string</td>
			<td>设备的产品id，从开放平台获取</td>
		</tr>

    	<tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回设备model</td>
		</tr>


	</tbody>
</table>

  

###7.2上传数据

  以字节形式上传蓝牙设备的数据，设备数据分为实时数据和历史数据
**接口方法**


   `HetThridBleApi getInstance().updateData()`

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

  	    <tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回设备model</td>
		</tr>

        <tr>
			<td>deviceModel</td>
			<td>是</td>
			<td>string</td>
			<td>设备的产品id，从开放平台获取</td>
		</tr>

  
        <tr>
			<td>datas</td>
			<td>是</td>
			<td>byte[]</td>
			<td>数据内容</td>
		</tr>


        <tr>
			<td>type </td>
			<td>是</td>
			<td>int</td>
			<td>1历史数据 2实时数据</td>
		</tr>


	</tbody>
</table>
        


 

###7.3获取历史数据
上传数据后，可以获取历史数据，数据以byte形式返回

**接口方法**


   `HetThridBleApi getInstance().etData()`

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

  	    <tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回设备model</td>
		</tr>

        <tr>
			<td>deviceModel</td>
			<td>是</td>
			<td>string</td>
			<td>设备的产品id，从开放平台获取</td>
		</tr>

  
        <tr>
			<td>order</td>
			<td>否</td>
			<td>int</td>
			<td>排序方式（0-降序 1-升序 默认0-降序）</td>
		</tr>


         <tr>
			<td>pageRows</td>
			<td>否</td>
			<td>int</td>
			<td>每页显示的行数(默认20)</td>
		</tr>

     <tr>
			<td>pageIndex</td>
			<td>否</td>
			<td>int</td>
			<td>当前页（默认1）</td>
		</tr>
	</tbody>
</table>
        



##9 和而泰通用HTTP请求

和而泰通用HTTP请求指，开发者通用调用sdk的通用http的api请求访问clife开放平台的相关功能，如菜谱，场景联动等功能
参考HetHttpApi，使用用户需参考相关api请求文档，
###9.1   通用GET请求不带参数
       
**接口方法**


   `HetHttpApi getInstance().hetGet()`

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        <tr>
			<td>url</td>
			<td>是</td>
			<td>string</td>
			<td>请求地址</td>
		</tr>

  	    <tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回json数据</td>
		</tr>



  
      
	</tbody>
</table>    
   
        
###9.2 通用GET请求带参数

**接口方法**


   `HetHttpApi getInstance().hetGet()`

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        <tr>
			<td>url</td>
			<td>是</td>
			<td>string</td>
			<td>请求地址</td>
		</tr>

       <tr>
			<td>params</td>
			<td>是</td>
			<td>请求参数</td>
			<td>请求地址</td>
		</tr>

  	    <tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回json数据</td>
		</tr>
      
	</tbody>
</table>   
         
###9.3 通用POST请求

**接口方法**


   `HetHttpApi getInstance().hetPost()`

**参数说明**
<table width="100%" style="border-spacing: 0;  border-collapse: collapse;">
	<tbody>
		<tr>
			<th width="16%">参数名称</th>
			<th width="11%">是否必须</th>
			<th width="11%">字段类型</th>
			<th width="62%">参数说明</th>
		</tr>

        <tr>
			<td>url</td>
			<td>是</td>
			<td>string</td>
			<td>请求地址</td>
		</tr>

       <tr>
			<td>params</td>
			<td>是</td>
			<td>请求参数</td>
			<td>请求地址</td>
		</tr>

  	    <tr>
			<td>callback</td>
			<td>是</td>
			<td>IHetCallback</td>
			<td>接口回调，成功返回json数据</td>
		</tr>
      
	</tbody>
 
  </table>    

##10 异常说明
   
###10.1 网络请求全局返回码说明
  
   | 返回码 | 说明  |
   | --- | --- |
   | 0 | 请求成功 |
   | 100010100 | 缺少授权信息 |
   |100010101  |AccessToken错误或已过期|
   |100010102|RefreshToken错误或已过期|
   |100010103|AppId不合法|
   |100010104|timestamp过期|
   |100010105|签名错误|
   |100010106|请求地址错误|
   |100010107|请求Scheme错误|
   |100010108|请求方法错误|
   |100010109|服务处理超时|
   |100010110|用户未登录|
   |100010200|失败|
   |100010201|缺少参数|
   |100010202|参数错误|
   |100010203|必须使用https|
   |100010204|参数删除不允许|
   |100010205|推荐位置相同|
   |100021000|帐号已注册|
   |100021001|帐号未注册|
   |100021002|帐号错误|
   |100021003|帐号未激活|
   |100021004|帐号未邀请|
   |100021005|帐号邀请状态|
   |100021006|帐号在其它手机已登录|
   |100021100|手机号码已注册|
   |100021101|手机号码未注册|
   |100021200|邮箱已注册|
   |100021201|邮箱未注册|
   |100021300|发送验证码失败|
   |100021301|验证码错误|
   |100021302|随机码错误|
   |100021400|用户已存在|
   |100021401|用户不存在|
   |100021500|密码错误|
   |100021603|数据不存在|
   |100022000|设备不存在|
   |100022001|设备未绑定|
   |100022002|设备已绑定|
   |100022003|设备解绑失败|
   |100022004|MAC地址已绑定另一种设备|
   |100022005|设备控制JSON错误|
   |100022006|设备不在线|
   |100022800|命令发送失败|
   |100022008|不能邀请自己控制|
   |100022009|关联的设备不存在|
   |100022010|关联的设备未绑定|
   |100022011|设备已授权|
   
   

###  10.2 设备无法绑定 
	  
1. 设备是否置为绑定模式，是否在绑定的有效时间内
2. 是否正确输入wifi密码,请确认手机是否能正常连接网络
3. 是扫描不到设备还是绑定不了设备,扫描失败会有对应提示是扫描不到设备，还是绑定不了设备
4. 设备是否已在CLife开放平台注册，并按照要求将大小类信息写入设备中
5. APP端服务是否开启（udpservice）
	   
	   

 
















     

  







 
 
  


     
     
     



