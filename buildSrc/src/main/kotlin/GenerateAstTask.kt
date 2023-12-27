package com.laracey.lox.codegen;

import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import java.io.File;
import com.laracey.lox.codegen.GenerateAst;

abstract class GenerateAstTask : DefaultTask() {
    @get:OutputDirectory
    abstract val outputDir : DirectoryProperty;

    @TaskAction
    fun generate() {
        val dir = outputDir.get().asFile
        logger.quiet("output dir = $dir")
        // val srcFile = File(dir, "Foo.java")
        // srcFile.writeText("package com.laracey.foo;\npublic class Foo {}")
        // System.out.println("hello from GenerateAstTask");
        GenerateAst.generate(dir)
    }
}

