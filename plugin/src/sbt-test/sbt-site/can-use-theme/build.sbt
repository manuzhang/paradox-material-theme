name := "test"

enablePlugins(ParadoxSitePlugin, ParadoxMaterialThemePlugin)

//#theme-site-settings
ParadoxMaterialThemePlugin.paradoxMaterialThemeSettings(Paradox)
//#theme-site-settings

paradoxProperties in Paradox ++= Map(
  "github.base_url" -> "https://github.com/jonas/paradox-material-theme"
)

//#enable-search
paradoxMaterialTheme in Paradox ~= {
  _.withSearch()
}
//#enable-search

paradoxMaterialTheme in Paradox ~= {
  _.withCopyright("OURS")
}

def fileContains(file: File, texts: String*) = {
  assert(file.exists, s"${file.getAbsolutePath} did not exist")
  val content = IO.readLines(file)
  for (text <- texts)
    assert(
      content.exists(_.contains(text)),
      s"Did not find '$text' in ${file.getName}:\n${content.mkString("\n")}"
    )
}

TaskKey[Unit]("checkContent") := {
  val dest = (target in makeSite).value / (siteSubdirName in Paradox).value

  fileContains(
    dest / "index.html",
    "Paradox Site", "Nicely themed", "mkdocs-material", "OURS"
  )

  fileContains(
    dest / "mkdocs" / "search_index.json",
    "Paradox Site", "Nicely themed"
  )
}
