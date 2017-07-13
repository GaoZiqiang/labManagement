package com.corejsf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
@Named("uploadRenderer")
@FacesRenderer(componentFamily = "javax.faces.Input", rendererType = "com.corejsf.Upload")
public class UploadRenderer extends Renderer {
	/**
	 * 呈现HTML元素
	 */
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		System.out.println("断点测试:encodeBegin()方法");
		if (!component.isRendered())
			return;
		ResponseWriter writer = context.getResponseWriter();

		String clientId = component.getClientId(context);
		System.out.println("打印clientId:  " + clientId);

		writer.startElement("input", component);
		writer.writeAttribute("type", "file", "type");
		writer.writeAttribute("name", clientId, "clientId");
		writer.endElement("input");
		writer.flush();
	}

	/**
	 * 检索servlet过滤器放在请求特性中的文件项，并且在标签特性的指示下处理它们
	 */
	public void decode(FacesContext context, UIComponent component) {
		System.out.println("断点测试:decode()方法");
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		String clientId = component.getClientId(context);
		FileItem item = (FileItem) request.getAttribute(clientId);

		Object newValue;
		ValueExpression valueExpr = component.getValueExpression("value");
		if (valueExpr != null) {
			Class<?> valueType = valueExpr.getType(context.getELContext());
			if (valueType == byte[].class) {
				newValue = item.get();
			} else if (valueType == InputStream.class) {
				try {
					newValue = item.getInputStream();
				} catch (IOException ex) {
					throw new FacesException(ex);
				}
			} else {
				String encoding = request.getCharacterEncoding();
				if (encoding != null)
					try {
						newValue = item.getString(encoding);
					} catch (UnsupportedEncodingException ex) {
						newValue = item.getString();
					}
				else
					newValue = item.getString();
			}
			((EditableValueHolder) component).setSubmittedValue(newValue);
			((EditableValueHolder) component).setValid(true);
		}

		Object target = component.getAttributes().get("target");

		if (target != null) {
			File file;
			if (target instanceof File)
				file = (File) target;
			else {
				ServletContext servletContext = (ServletContext) external.getContext();
				// 文件路径
				String realPath = servletContext.getRealPath(target.toString());
				// 打印输出路径
				System.out.println("打印realPath:  " + realPath);
				// 保存路径
				file = new File(realPath);
			}

			try { // ugh--write is declared with "throws Exception"
				item.write(file);
			} catch (Exception ex) {
				throw new FacesException(ex);
			}
		}
	}
}