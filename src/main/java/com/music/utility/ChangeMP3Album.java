package com.music.utility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

public class ChangeMP3Album {

	public static void main(String[] args) {
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		String path = "H:\\Music\\todo";
		// updateSingleFolder();
		rename2(path);
		deleteAlbum(path);
		updateAllFileInFolders(path);

	}

	private static void updateAllFileInFolders(String path) {
		File baseFolder = new File(path);
		File[] listFolders = baseFolder.listFiles();
		Map<String, String> errorDetails = new HashMap<>();
		for (File folder : listFolders) {
			if (folder.isDirectory()) {
				File[] files = folder.listFiles();
				if (files != null) {
					for (File mp3File : files) {
						if (mp3File.getName().toLowerCase().endsWith(".mp3")) {
							try {
								String song = mp3File.getName().replace(".mp3", "");
								AudioFile audioFile = AudioFileIO.read(mp3File);
								Tag tag = new ID3v1Tag();
								tag.setField(FieldKey.ALBUM, folder.getName());
								tag.setField(FieldKey.TITLE, song);
								tag.setField(FieldKey.GENRE, folder.getName());
								audioFile.setTag(tag);

								ID3v24Tag id3v2Tag = new ID3v24Tag();
								id3v2Tag.setField(FieldKey.GENRE, folder.getName());
								audioFile.setTag(id3v2Tag);

								audioFile.commit();
								System.out.println("Folder : " + folder.getName() + ", File : " + mp3File.getName());
							} catch (Exception e) {
								errorDetails.put("Folder : " + folder.getName() + ", File : " + mp3File.getName(),
										e.getMessage());
							}
						}
					}
				}
			}
		}

		for (String error : errorDetails.keySet()) {
			System.err.println(error + "Message - " + errorDetails.get(error));
		}

	}

	private static void rename2(String path) {

		File baseFolder = new File(path);
		File[] listFolders = baseFolder.listFiles();
		Map<String, String> errorDetails = new HashMap<>();
		for (File folder : listFolders) {
			if (folder.isDirectory()) {
				File[] files = folder.listFiles();
				if (files != null) {
					for (File mp3File : files) {
						if (mp3File.getName().toLowerCase().endsWith(".mp3")) {
							try {
								String name = mp3File.getName();
								name = name.replace("＂", "");
								name = name.replace(".mp3", "");
								name = name.replace("(Official Video)", "");
								name = name.replace("(Official Music Video)", "");
								String newFileName = name.split("\\[")[0];
								newFileName = newFileName.trim();
								System.out.println(newFileName + ".mp3");
								File newFile = new File(folder, newFileName + ".mp3");
								mp3File.renameTo(newFile);
							} catch (Exception e) {
								errorDetails.put("Folder : " + folder.getName() + ", File : " + mp3File.getName(),
										e.getMessage());
							}
						}
					}
				}
			}
		}
	}

	private static void deleteAlbum(String path) {
		File baseFolder = new File(path);
		File[] listFolders = baseFolder.listFiles();
		Map<String, String> errorDetails = new HashMap<>();
		for (File folder : listFolders) {
			if (folder.isDirectory()) {
				File[] files = folder.listFiles();
				if (files != null) {
					for (File mp3File : files) {
						if (mp3File.getName().toLowerCase().endsWith(".mp3")) {
							try {
								MP3File mmm = (MP3File) AudioFileIO.read(mp3File);
								mmm.setID3v1Tag(null);
								mmm.setID3v2Tag(null);
								mmm.setTag(null);
								mmm.commit();
								System.out.println("Folder : " + folder.getName() + ", File : " + mp3File.getName());
							} catch (Exception e) {
								errorDetails.put("Folder : " + folder.getName() + ", File : " + mp3File.getName(),
										e.getMessage());
							}
						}
					}
				}
			}
		}

		for (String error : errorDetails.keySet()) {
			System.err.println(error + "Message - " + errorDetails.get(error));
		}

	}

	private static void updateSingleFolder() {
		String folderName = "BhagawadGeeta";

		File folder = new File("C:\\test\\" + folderName);

		if (!folder.exists() || !folder.isDirectory()) {
			System.out.println("Invalid folder path.");
			return;
		}

		File[] files = folder.listFiles();

		if (files != null) {
			for (File mp3File : files) {
				if (mp3File.getName().toLowerCase().endsWith(".mp3")) {
					try {
						AudioFile audioFile = AudioFileIO.read(mp3File);
						Tag tag = audioFile.getTag();
						if (tag == null) {
							tag = new ID3v24Tag(); // Create a new ID3v2.4 tag
							audioFile.setTag(tag);
						}
						tag.setField(FieldKey.ALBUM, folderName);
						audioFile.commit();

						System.out.println("Album name updated successfully.");
					} catch (Exception e) {
						System.err.println("Error updating album: " + e.getMessage());
					}

				}
			}
		}

	}

}
