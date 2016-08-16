# ueditor-springmvc
ueditor集成SpringMvc 

改变了原有只是jsp的文件上传逻辑、默认的配置读取方式。

### Useage:

pull代码

```
git clone https://github.com/peachyy/ueditor-springmvc.git

```

build

```
cd ueditor-springmvc

maven install 

````

引用

```
		<dependency>
			<groupId>com.peachyy</groupId>
			<artifactId>ueditor-springmvc</artifactId>
			<version>1.0</version>
		</dependency>
```		

定义一个读取Uditor配置的controller


```
@Controller
@RequestMapping("/support/ueditor")
public class SpringMVCUEditorSupport extends SpringMvcUeditorSupport {

}		
```

配置页面UEDITOR_HOME_URL


```
window.UEDITOR_HOME_URL=${you path}/support/ueditor
```

end 

