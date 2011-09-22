/*
 * This file is part of jacoco4sbt.
 * 
 * Copyright (c) 2011 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.johoop.jacoco4sbt

import sbt._
import Keys._
import org.jacoco.core.runtime.LoggerRuntime

object JacocoPlugin extends Plugin {
  object jacoco extends Commands with Keys {

    def reportAction(jacocoDirectory: File, reportFormats: Seq[FormattedReport], reportTitle: String,
        sourceDirectories: Seq[File], classDirectories: Seq[File], sourceEncoding: String, tabWidth: Int) = {

      val report = new Report(
          reportDirectory = jacocoDirectory,
          executionDataFile = jacocoDirectory / "jacoco.exec",
          reportFormats = reportFormats,
          reportTitle = reportTitle,
          classDirectories = classDirectories,
          sourceDirectories = sourceDirectories,
          tabWidth = tabWidth,
          sourceEncoding = sourceEncoding)
      
      report.generate
    }

    val settings : Seq[Setting[_]] = Seq(
      commands += jacocoCommand,
      ivyConfigurations += Config,
      instrumentedClassDirectory in Compile <<= (outputDirectory in Config, classDirectory in Compile) (_ / _.getName),
      instrumentedClassDirectory in Test <<= (outputDirectory in Config, classDirectory in Test) (_ / _.getName)
    ) ++ 
    inConfig(Config)(Seq(
      outputDirectory <<= (crossTarget) { _ / "jacoco" },
      reportFormats := Seq(HTMLReport()),
      reportTitle := "Jacoco Coverage Report",
      sourceTabWidth := 2,
      sourceEncoding := "utf-8",
      
      combinedClassDirectories <<= (classDirectory in Compile, classDirectory in Test) map (Seq(_, _)),
      jacocoSources <<= (sourceDirectories in Compile, sourceDirectories in Test) map (_++_),
        
      jacocoReport <<= 
          (outputDirectory, reportFormats, reportTitle, 
              jacocoSources, combinedClassDirectories, sourceEncoding, sourceTabWidth) map reportAction))
  }
}
