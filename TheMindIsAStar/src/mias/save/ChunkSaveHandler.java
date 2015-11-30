package mias.save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import mias.world.Chunk;
import mias.world.ChunkProvider;

public class ChunkSaveHandler {

	protected static File savePath = new File("chunks");

	public static void saveChunk(Chunk c) {
		if (savePath != null) {
			String file_name = chunkFileName(c.getChunkX(), c.getChunkY(), c.getChunkZ());
			File f = new File(savePath, file_name + "_temp.cnk");
			try {
				FileOutputStream stream = new FileOutputStream(f);
				stream.write(c.toByteBuffer().array());
				stream.close();
				f.renameTo(new File(f + ".cnk"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Chunk loadChunk(int x, int y, int z) {
		if (savePath != null) {
			String file_name = chunkFileName(x, y, z);
			File f = new File(savePath, file_name + ".cnk");
			try {
				Chunk c;
				if(f.exists()){
					c = new Chunk(x, y, z, false);
					FileInputStream stream = new FileInputStream(f);
					ByteBuffer bb = ByteBuffer.allocate(Chunk.CHUNK_DATA_SIZE);
					stream.read(bb.array());
					stream.close();
					c.fromByteBuffer(bb);
				}
				else{
					c = new Chunk(x, y, z, true);
					ChunkProvider.setDefaultChunk(c);
				}
				return c;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String chunkFileName(int x, int y, int z) {
		return "c" + Integer.toString(x) + "-" + Integer.toString(y) + "-" + Integer.toString(z);
	}
}
