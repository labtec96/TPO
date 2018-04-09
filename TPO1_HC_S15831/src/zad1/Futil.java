package zad1;


import java.io.IOException;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.nio.file.StandardOpenOption.CREATE;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;


public class Futil
{
	static Charset inCharset  = Charset.forName("Cp1250");
	static Charset outCharset = Charset.forName("UTF-8");
	static FileChannel fcin;
	static ByteBuffer buf;
	static CharBuffer cbuf;
	static FileChannel fcout;
	public static void processDir(String dirName, String resultFileName)
	{
		
		try
		{ 
			fcout = FileChannel.open(Paths.get(resultFileName), EnumSet.of(CREATE,WRITE));
			System.out.println(Paths.get(resultFileName));
			Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>()
			{
				@Override
				public FileVisitResult visitFile(Path dir, BasicFileAttributes attrs) throws IOException 
				{
					
					String file = dir.getFileName().toString();
					file = file.substring(file.length() - 3, file.length());
					if (file.equals("txt"))
					{
						
						fcin = FileChannel.open(dir);
						buf = ByteBuffer.allocate((int)fcin.size());
						
						fcin.read(buf);
						buf.flip();
					    cbuf = inCharset.decode(buf);
					    buf = outCharset.encode(cbuf);
					    fcout.write(buf);
					    fcin.close();
					}
					return FileVisitResult.CONTINUE;
				}
				
			});
			fcout.close();
		} catch (Exception ex)
		{
			System.err.println(ex);
			System.out.println("Blad wczytywania Folderu");
		}
	}
}
