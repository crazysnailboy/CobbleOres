package net.crazysnailboy.mods.cobbleores.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import net.crazysnailboy.mods.cobbleores.CobbleOresMod;
import net.minecraftforge.fml.common.Loader;

public class FileUtils
{

	public static String readFileContents(String filename)
	{
		String fileContents = "";

		File configFolder = new File((Loader.instance().getConfigDir().getAbsolutePath() + "/" + CobbleOresMod.MODID).replace(File.separatorChar, '/').replace("/./", "/"));
		if (!configFolder.exists()) configFolder.mkdirs();

		File file = new File(configFolder, filename);
		if (file.exists())
		{
			fileContents = readFileFromFolder(file);
		}
		else
		{
			fileContents = readFileFromJar("assets/" + CobbleOresMod.MODID + "/data/" + filename);
			writeFile(new File(configFolder, filename), fileContents);
		}

		return fileContents;
	}




	private static String readFileFromFolder(File file)
	{
		String fileContents = "";
		try
		{
			InputStream stream = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

			fileContents = IOUtils.toString(stream);

			reader.close();
			stream.close();

		}
		catch(Exception ex){ CobbleOresMod.logger.catching(ex); }
		return fileContents;
	}

	private static String readFileFromJar(String fileName)
	{
		String fileContents = "";
		try
		{
			InputStream stream = CobbleOresMod.INSTANCE.getClass().getClassLoader().getResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

			fileContents = IOUtils.toString(stream);

			reader.close();
			stream.close();

		}
		catch(Exception ex){ CobbleOresMod.logger.catching(ex); }
		return fileContents;
	}

	private static void writeFile(File outputFile, String fileContents)
	{
		try
		{
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			BufferedWriter streamWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

			streamWriter.write(fileContents);

			streamWriter.close();
			outputStream.close();

		}
		catch(Exception ex){ CobbleOresMod.logger.catching(ex); }
	}


}
