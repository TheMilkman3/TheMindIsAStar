package mias.save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import mias.world.Chunk;

public class ChunkSaveHandler {
	
	protected File savePath;
	
	public void saveChunk(Chunk c) {
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
	
	public void loadChunk(Chunk c) {
		if(savePath != null) {
			String file_name = chunkFileName(c.getChunkX(), c.getChunkY(), c.getChunkZ());
			File f = new File(savePath, file_name + ".cnk");
			try {
				FileInputStream stream = new FileInputStream(f);
				ByteBuffer bb = ByteBuffer.allocate(4 + Chunk.getSize() * 2);
				stream.read(bb.array());
				stream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean loadChunk(int x, int y, int z) {
		if (savePath != null) {
		}
		return false;
	}
	
	private String chunkFileName(int x, int y, int z) {
		return "c" + Integer.toString(x) + "-" + Integer.toString(y) + "-" + Integer.toString(z);
	}
}
