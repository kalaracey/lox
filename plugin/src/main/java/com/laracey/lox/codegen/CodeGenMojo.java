package com.laracey.lox.codegen;

import java.io.IOException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import org.apache.maven.project.MavenProject;

@Mojo(name = "code-gen", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CodeGenMojo extends AbstractMojo {
  @Parameter (defaultValue="${project}", required=true, readonly=true)
  private MavenProject project;

  @Parameter(defaultValue = "${project.build.directory}/generated-sources", required = true)
  private File generatedSourcesDirectory;

  public void execute() throws MojoExecutionException {
    getLog()
        .info("Generating AST file in " + generatedSourcesDirectory.getPath());
    try {
    GenerateAst.generate(generatedSourcesDirectory.getPath());
    } catch (IOException e) {
      throw new MojoExecutionException("Failed to generate AST file", e);
    }
    project.addCompileSourceRoot(generatedSourcesDirectory.getPath());
  }
}
