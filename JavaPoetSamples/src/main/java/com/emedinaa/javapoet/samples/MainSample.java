package com.emedinaa.javapoet.samples;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

/**
 * Created by eduardo on 13/11/16.
 * https://github.com/square/javapoet
 */
public final class MainSample {

    private static final String PACKAGENAME= "com.emedinaa.javapoet.samples";
    private static  File sourcePath = new File("out");


    public static void main(String[] args) {
        System.out.println("Hello, JavaPoet!");
        //buildHelloWorld();
        //buildControlFlow();
        //buildConstructor();
        //buildFields();
        //buildInterfaces();
        buildEnum();
    }

    private static void buildHelloWorld() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        generate(PACKAGENAME,helloWorld,sourcePath);
    }

    private static void buildControlFlow()
    {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .addStatement("total += i")
                .endControlFlow()
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("ControlFlow")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        generate(PACKAGENAME,helloWorld,sourcePath);
    }

    private static void buildConstructor(){

        MethodSpec flux = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "greeting")
                .addStatement("this.$N = $N", "greeting", "greeting")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("Constructor")
                .addModifiers(Modifier.PUBLIC)
                .addField(String.class, "greeting", Modifier.PRIVATE, Modifier.FINAL)
                .addMethod(flux)
                .build();

        generate(PACKAGENAME,helloWorld,sourcePath);
    }

    private static void buildFields() {
        FieldSpec android = FieldSpec.builder(String.class, "android")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("Fields")
                .addModifiers(Modifier.PUBLIC)
                .addField(android)
                .addField(String.class, "robot", Modifier.PRIVATE, Modifier.FINAL)
                .build();

        generate(PACKAGENAME,helloWorld,sourcePath);
    }

    private static void buildInterfaces(){
        TypeSpec helloWorld = TypeSpec.interfaceBuilder("MyInterface")
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(String.class, "ONLY_THING_THAT_IS_CONSTANT")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", "change")
                        .build())
                .addMethod(MethodSpec.methodBuilder("beep")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        generate(PACKAGENAME,helloWorld,sourcePath);
    }

    private static void buildEnum() {
        TypeSpec helloWorld = TypeSpec.enumBuilder("MyEnum")
                .addModifiers(Modifier.PUBLIC)
                .addEnumConstant("ROCK")
                .addEnumConstant("SCISSORS")
                .addEnumConstant("PAPER")
                .build();
        generate(PACKAGENAME,helloWorld,sourcePath);

    }

    private static void generate(String packageName, TypeSpec typeSpec, File file) {

        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();

        try {
            javaFile.writeTo(System.out);
            if(file!=null)javaFile.writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
