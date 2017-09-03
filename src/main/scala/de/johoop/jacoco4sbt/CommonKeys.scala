/*
 * This file is part of jacoco4sbt.
 *
 * Copyright (c) Joachim Hofer & contributors
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package de.johoop.jacoco4sbt

import sbt._

private[jacoco4sbt] trait CommonKeys {
  val jacoco: TaskKey[Unit] = taskKey[Unit]("Executes the tests and creates a JaCoCo coverage report.")

  val jacocoCheck: TaskKey[Unit] = taskKey[Unit]("Executes the tests and saves the execution data in 'jacoco.exec'.")
  val jacocoReport: TaskKey[Unit] =
    taskKey[Unit]("Generates a JaCoCo report. You can use the 'jacoco report' command alternatively.")

  val jacocoAggregate: TaskKey[Unit] = taskKey[Unit](
    "Executes the tests and creates a JaCoCo coverage report as well as an aggregated report which merges all sub-projects.")

  val jacocoAggregateReport: TaskKey[Unit] = taskKey[Unit]("Generates an aggregated JaCoCo report.")

  private[jacoco4sbt] val coveredSources: TaskKey[Seq[File]] = taskKey[Seq[File]]("Covered Sources.")

  private[jacoco4sbt] val aggregateCoveredSources: TaskKey[Seq[File]] =
    taskKey[Seq[File]]("Covered Sources across all aggregated projects.")
  private[jacoco4sbt] val classesToCover: TaskKey[Seq[File]] =
    taskKey[Seq[File]]("compiled classes (filtered by includes and excludes) that will be covered")
  private[jacoco4sbt] val aggregateClassesToCover: TaskKey[Seq[File]] = taskKey[Seq[File]](
    "compiled classes (filtered by includes and excludes) that will be covered across all aggregated project")

  private[jacoco4sbt] val aggregateExecutionDataFiles: TaskKey[Seq[File]] =
    taskKey[Seq[File]]("All execution data output files for aggregated modules.")

  val outputDirectory: SettingKey[File] =
    settingKey[File]("Where JaCoCo should store its execution data and reports.")
  val aggregateReportDirectory: SettingKey[File] =
    settingKey[File]("Where JaCoCo should store its aggregate reports.")
  val executionDataFile: SettingKey[File] = settingKey[File]("Execution data output file.")
  val reportTitle: SettingKey[String] = settingKey[String]("Title of the JacoCo report to generate.")
  val aggregateReportTitle: SettingKey[String] =
    settingKey[String]("Title of the JacoCo aggregate report to generate.")
  val reportFormats: SettingKey[Seq[FormattedReport]] = SettingKey[Seq[FormattedReport]](
    "report-formats",
    "Set of formats (XML, CSV, HTML) of the JaCoCo reports to generate.")

  val sourceTabWidth: SettingKey[Int] =
    settingKey[Int]("Tab width of the sources to display in the JaCoCo reports.")
  val sourceEncoding: SettingKey[String] =
    settingKey[String]("Encoding of the source files (for JaCoCo reporting).")


  val includes: SettingKey[Seq[String]] = settingKey[Seq[String]](
    "glob patterns specifying which classes to cover; excludes override includes; default: all classes included")

  val excludes: SettingKey[Seq[String]] = settingKey[Seq[String]](
    "glob patterns specifying which classes not to cover; excludes override includes; default: no classes excluded")

  val instrumentedClassDirectory: SettingKey[File] =
    settingKey[File]("Directory containing the instrumented classes.")

  /**
    * Example - in build.sbt add
    * jacoco.thresholds in jacoco.Config := Thresholds(instruction = 35, method = 40, branch = 30, complexity = 35, line = 50, clazz = 40)
    */
  val thresholds: SettingKey[Thresholds] = settingKey[Thresholds]("Required coverage ratios.")
  val aggregateThresholds: SettingKey[Thresholds] =
    settingKey[Thresholds]("Required coverage ratios for the aggregate report.")
}
