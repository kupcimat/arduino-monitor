organization  := "org.kupcimat"

name          := "arduino-monitor"

version       := "0.1"

scalaVersion  := "2.11.5"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion = "2.3.6"
  val sprayVersion = "1.3.2"
  Seq(
    "io.spray"           %% "spray-can"     % sprayVersion,
    "io.spray"           %% "spray-routing" % sprayVersion,
    "io.spray"           %% "spray-json"    % "1.3.1",
    "io.spray"           %% "spray-testkit" % sprayVersion % "test",
    "com.typesafe.akka"  %% "akka-actor"    % akkaVersion,
    "com.typesafe.akka"  %% "akka-testkit"  % akkaVersion  % "test",
    "com.typesafe"       %  "config"        % "1.2.1",
    "com.h2database"     %  "h2"            % "1.4.185",
    "org.slf4j"          %  "slf4j-nop"     % "1.6.4",
    "org.specs2"         %% "specs2-core"   % "2.3.11"     % "test"
  )
}

Revolver.settings
