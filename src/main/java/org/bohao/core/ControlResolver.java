package org.bohao.core;

import org.bohao.annotation.Controller;
import org.bohao.annotation.RequestMapping;
import org.bohao.exception.ResourceNotFoundException;
import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by bohao on 03-29-0029.
 */
public class ControlResolver {
    private static final Logger logger = LoggerFactory.getLogger(ControlResolver.class);


    public void process(HttpRequest request, HttpResponse response) {
        Class best = null;
        String bestLen = "";
        try {
            Iterable<Class> classes = getClasses("org.bohao.core");
            for (Class myclass : classes) {
                if (myclass.isAnnotationPresent(Controller.class)) {
                    String path = ((Controller) myclass.getAnnotation(Controller.class)).value();
                    if (!request.getContextPath().startsWith(path))
                        continue;

                    if (best == null || bestLen.length() < path.length()) {
                        best = myclass;
                        bestLen = path;
                    }
                }
            }

            if (best != null) {
                logger.info("{}", best.getName());
                boolean ret = findMethod(best, request, response);
                if (ret)
                    return;
            }

        } catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage());
        } catch (Exception e) {
            // TODO: �����г��ֵĴ������ڶ��׳�404
            // ���Կ�������������
            logger.warn("failed for unkown reasons, {}", request.getContextPath());
        }

        NotFoundController defaultCtrl = new NotFoundController();
        defaultCtrl.doGet(request, response);
    }

    /**
     * ��Controller����RequestMapping������
     * ȡ�ƥ��
     * ��������Ĳ������ԣ�gua��Խ��Խ��
     *
     * @param myclass  controller��
     * @param request  ����
     * @param response �ظ�
     * @return �Ƿ��ҵ�process����
     */
    private boolean findMethod(Class myclass, HttpRequest request, HttpResponse response) {
        Method best = null;
        String bestLen = "";
        for (Method method : myclass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = method.getAnnotation(RequestMapping.class);

                if (!request.getContextPath().startsWith(mapping.value()))
                    continue;

                if (!request.getMethod().toUpperCase().equals(mapping.method().toUpperCase()))
                    continue;

                if (best == null) {
                    best = method;
                    bestLen = mapping.value();
                } else if (bestLen.length() < mapping.value().length()) {
                    best = method;
                    bestLen = mapping.value();
                }
            }
        }

        try {
            if (best != null) {
                best.invoke(myclass.newInstance(), request, response);
                return true;
            }
        } catch (InvocationTargetException e) {
            // FileToStr.image �ᴥ�����˴�
            throw new ResourceNotFoundException(request.getContextPath() + " not found");
        } catch (IllegalAccessException | InstantiationException e) {
            logger.info("request mapping args error! {}", myclass.getName());
        }
        return false;
    }

    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
