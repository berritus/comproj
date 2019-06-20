package com.berritus.mis.common;

import com.berritus.mis.bean.mybatis.SysServiceConfig;
import com.berritus.mis.controller.conf.MisApplication;
import com.berritus.mis.dubbo.api.IFlowService;
import com.berritus.mis.dubbo.api.IMsgSendService;
import com.berritus.mis.service.base.BaseServiceEngine;
import com.berritus.mis.service.base.DispatchBaseService;
import com.berritus.mis.service.base.MsgSendBaseService;
import com.berritus.mis.service.impl.AbstractFlowService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.GenericTypeResolver;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.*;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-16 11:23
 * @RunWith(SpringRunner.class)
 * @SpringBootTest(classes = MisApplication.class)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisApplication.class)
public class ServiceEngineTest {
	@Autowired
	private BaseServiceEngine baseServiceEngine;

	@Autowired
	private MsgSendBaseService msgSendBaseService;

	@Test
	public void test1() {
		msgSendBaseService.sendSms("hello");
		System.out.println("success");
	}

	@Test
	public void test2() {
		SysServiceConfig sysServiceConfig = new SysServiceConfig();
		sysServiceConfig.setServiceInterface("com.berritus.mis.dubbo.api.DubboDemoService");
		sysServiceConfig.setApplicationCode("mis-521");
		msgSendBaseService.helloDubbo(sysServiceConfig, "hello");
		System.out.println("success");
	}

	@Test
	public void test3() throws Exception{

		IFlowService flowService = new AbstractFlowService();

		DispatchBaseService handler = new DispatchBaseService(flowService);

		IFlowService proxy = (IFlowService) Proxy.newProxyInstance(flowService.getClass().getClassLoader(), flowService.getClass().getInterfaces(), handler);

		proxy.method3();
	}

	@Test
	public void test4() {
		MsgSendBaseService msgSendBaseService = new MsgSendBaseService();
		Type types = msgSendBaseService.getClass().getGenericSuperclass();
		Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
		for (Type t : genericType) {
			System.out.println(t.getTypeName());
		}
	}

	public static void main(String[] args) {
		IFlowService flowService = new AbstractFlowService();

		DispatchBaseService handler = new DispatchBaseService(flowService);

		IFlowService proxy = (IFlowService) Proxy.newProxyInstance(flowService.getClass().getClassLoader(), flowService.getClass().getInterfaces(), handler);

		proxy.method3();
	}

//	private static Class[] doResolveTypeArguments(Class ownerClass, Class classToIntrospect, Class genericIfc) {
//		while (classToIntrospect != null) {
//			if (genericIfc.isInterface()) {
//				Type[] ifcs = classToIntrospect.getGenericInterfaces();
//				for (Type ifc : ifcs) {
//					Class[] results = doResolveTypeArguments(ownerClass, ifc, genericIfc);
//					if (results != null) {
//						return results;
//					}
//				}
//			} else {
//				Class[] results = doResolveTypeArguments(ownerClass, classToIntrospect.getGenericSuperclass(), genericIfc);
//				if (results != null) {
//					return results;
//				}
//			}
//
//			classToIntrospect = classToIntrospect.getSuperclass();
//		}
//
//		return null;
//	}
//
//	private static Class[] doResolveTypeArguments(Class ownerClass, Type ifc, Class genericIfc) {
//		if (ifc instanceof ParameterizedType) {
//			ParameterizedType paramIfc = (ParameterizedType)ifc;
//			Type rawType = paramIfc.getRawType();
//			if (genericIfc.equals(rawType)) {
//				Type[] typeArgs = paramIfc.getActualTypeArguments();
//				Class[] results = new Class[typeArgs.length];
//				for (int i = 0; i < typeArgs.length; i++) {
//					Type arg = typeArgs[i];
//					results[i] = extractClass(ownerClass, arg);
//				}
//				return results;
//			}else if (genericIfc.isAssignableFrom((Class)rawType)) {
//				return doResolveTypeArguments(ownerClass, (Class)rawType, genericIfc);
//			}
//		}else if (ifc != null && genericIfc.isAssignableFrom((Class)ifc)) {
//			return doResolveTypeArguments(ownerClass, (Class)ifc, genericIfc);
//		}
//
//		return null;
//	}
//
//	@Test
//	public void testGenericType() {
//		StringToBoolean params = new StringToBoolean();
//		Class[] genericArgs = GenericTypeResolver.resolveTypeArguments(params.getClass(), Converter.class);
//		Assert.assertEquals(String.class, genericArgs[0]);
//		Assert.assertEquals(Boolean.class, genericArgs[1]);
//	}
//
//	interface Converter<S, T> {
//
//	}
//
//	class StringToBoolean implements Converter<String, Boolean> {
//
//	}
//
//	private Class extractClass(Type type) {
//		Class result = null;
//		if (type instanceof Class)
//			result = (Class) type;
//		if (type instanceof WildcardType) {
//			if (((WildcardType) type).getLowerBounds().length > 0) {
//				result = extractClass(((WildcardType) type).getLowerBounds()[0]);
//			} else if (((WildcardType) type).getUpperBounds().length > 0) {
//				result = extractClass(((WildcardType) type).getUpperBounds()[0]);
//			}
//		} else if (type instanceof ParameterizedType) {
//			result = extractClass(((ParameterizedType) type).getActualTypeArguments()[0]);
//		} else if (type instanceof TypeVariable) {
//			TypeVariable tv = (TypeVariable) type;
//
//			Class c = getEntityClass();
//			result = extractTypeVariableBounds(tv, c);
//			if (result == null)
//				result = extractTypeVariableBounds(tv, (Class) tv.getGenericDeclaration());
//			if (result == null && tv.getBounds().length > 0)
//				result = extractClass(tv.getBounds()[0]);
//
//		}
//		return result;
//	}
//
//	private Class extractTypeVariableBounds(TypeVariable tv, Class c) {
//		if (c == null)
//			return Object.class;
//
//		Type genericSuperclass = c.getGenericSuperclass();
//		if (genericSuperclass != null) {
//			int index = 0;
//			for (TypeVariable dtv : c.getSuperclass().getTypeParameters()) {
//				if (dtv.equals(tv)) {
//					while (genericSuperclass instanceof Class) {
//						genericSuperclass = ((Class) genericSuperclass).getGenericSuperclass();
//					}
//					if (genericSuperclass != null)
//						return extractClass(((ParameterizedType) genericSuperclass).getActualTypeArguments()[index]);
//				}
//				index++;
//			}
//			c = c.getSuperclass();
//			return extractTypeVariableBounds(tv, c.getSuperclass());
//		}
//		if (tv.getBounds().length > 0)
//			return extractClass(tv.getBounds()[0]);
//		return Object.class;
//	}
}
