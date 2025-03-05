/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psimulator;

import halting.HaltingCondition;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mode.DerivationMode;
import mode.FilterDerivationMode;

/**
 *
 * @author ver
 */
public class PSystemComponentsList {

    public static Collection<String> dmList() {
        ArrayList<String> list = new ArrayList<String>(constructMap("mode.standard").keySet());
        list.addAll(constructMap("mode.basic").keySet());
        return list;
    }

    public static List<DerivationMode> dmInstanceList() {
        DerivationMode dm = null;
        List<DerivationMode> res = getAllInstances(dm, "mode.standard");
        res.addAll(getAllInstances(dm, "mode.basic"));
        return res;
    }

    public static Collection<String> haltingList() {
        return constructMap("halting").keySet();
    }

    public static List<HaltingCondition> haltingInstanceList() {
        HaltingCondition h = null;
        return getAllInstances(h, "halting.standard");
    }

    public static Collection<String> filterList() {
        return constructMap("mode.filter").keySet();
    }
    
    public static FilterDerivationMode filterInstance(String name, DerivationMode base){
        Class[] cl= new Class[1];
        cl[0]=DerivationMode.class;
        Object[] arg = new Object[1];
        arg[0] = base;
        return (FilterDerivationMode)PSystemComponentsList.getInstance(base, name, "mode.filter", cl, arg);
    }

    public static Collection<String> listNames(String path) {
        return constructMap(path).keySet();
    }

    public static <T> T getInstance(T type, String name, String path) {
        return getInstance(type, name, path, null, null);
    }

    public static <T> T getInstance(T type, String name, String path, Class[] params, Object[] args) {
        Map<String, Class> list = constructMap(path);
        if (!list.containsKey(name)) {
            return null;
        }
        T instance = null;
        Class cl = list.get(name);
        try {

            instance = (T) cl.getConstructor(params).newInstance(args);

        } catch (NoSuchMethodException ex) {
            //   Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, cl.getName(), ex);
            // abstract 
            // all others shouldn't occur   
        } catch (SecurityException ex) {
            Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }

    public static <T> List<T> getAllInstances(T type, String path) {
        return getAllInstances(type, path, null, null);
    }

    public static <T> List<T> getAllInstances(T type, String path, Class[] params, Object[] args) {
        List<T> res = new ArrayList<T>();
        Map<String, Class> list = constructMap(path);
        for (Class cl : list.values()) {
            try {

                T val = (T) cl.getConstructor(params).newInstance(args);
                res.add(val);

            } catch (NoSuchMethodException ex) {
                //   Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, cl.getName(), ex);
                // abstract 
                // all others shouldn't occur   
            } catch (SecurityException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    private static Map<String, Class> constructMap(String path) {
        HashMap<String, Class> list = new HashMap<String, Class>();
        for (Class cl : PackageList.getClassesInPackage(path)) {
            try {
                Field f = cl.getDeclaredField("publicName");
                if (Modifier.isStatic(f.getModifiers())) {
                    String x = (String) f.get(null);
                    list.put(x, cl);
                }
            } catch (NoSuchFieldException ex) {
                // Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    /*
    
    public static <T> List<T> listToString(T type, String path) {
    List<T> list = new ArrayList<T>();
    for (Class cl : PackageList.getClassesInPackage(path)) {
    
    try {
    
    T dm = (T) cl.getConstructor().newInstance();
    list.add(dm);
    
    } catch (NoSuchMethodException ex) {
    //   Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, cl.getName(), ex);
    // abstract 
    // all others shouldn't occur   
    } catch (SecurityException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvocationTargetException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    return list;
    }
    
    public static <T> List<T> list(T type, String path) {
    List<T> list = new ArrayList<T>();
    for (Class cl : PackageList.getClassesInPackage(path)) {
    
    try {
    Field f = cl.getDeclaredField("publicName");
    if (Modifier.isStatic(f.getModifiers())) {
    T dm = (T) cl.getConstructor().newInstance();
    list.add(dm);
    }
    } catch (NoSuchFieldException ex) {
    //   Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SecurityException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NoSuchMethodException ex) {
    //   Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, cl.getName(), ex);
    // abstract 
    // all others shouldn't occur   
    } catch (InstantiationException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvocationTargetException ex) {
    Logger.getLogger(PSystemComponentsList.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    return list;
    }
    
    
    
     */
}
