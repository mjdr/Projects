package music;

public class WavInfo 
{
	
	private int chunkSize;
	private int numChannels;
	private int sampleRate;
	private int byteRate;
	private int blockAlign;
	private int bitsPerSample;
	private int subchunk2Size;
	
	
	
	public WavInfo(int chunkSize, int numChannels,
			int sampleRate, int byteRate, int blockAlign, int bitsPerSample,
			int subchunk2Size) 
	{
		this.chunkSize = chunkSize;
		this.numChannels = numChannels;
		this.sampleRate = sampleRate;
		this.byteRate = byteRate;
		this.blockAlign = blockAlign;
		this.bitsPerSample = bitsPerSample;
		this.subchunk2Size = subchunk2Size;
	}

	/**Это оставшийся размер цепочки, начиная
	 * с этой позиции. Иначе говоря, это размер файла - 8, 
	 * то есть, исключены поля chunkId и chunkSize.
	 * */
	public int getChunkSize()
	{
		return chunkSize;
	}
	/**
	 * Количество каналов. 
	 * Моно = 1, Стерео = 2 и т.д.
	 * */
	public int getNumChanels()
	{
		return numChannels;
	}
	/**
	 * Частота дискретизации. 
	 * 8000 Гц, 44100 Гц и т.д.
	 * */
	public int getSampleRate()
	{
		return sampleRate;
	}
	/**
	 * Количество байт, переданных за секунду воспроизведения.
	 * */
	public int getByteRate()
	{
		return byteRate;
	}
	
	/** 
	 * Количество байт для одного сэмпла, включая все каналы.
	 * */
	public int getBlockAlign()
	{
		return blockAlign;
	}
	
	/**
	 * Количество бит в сэмпле. Так называемая "глубина" или точность звучания. 8 бит, 16 бит и т.д.
	 * */
	public int getBitsPerSample()
	{
		return bitsPerSample;
	}
	
	/**
	 * Количество байт в области данных.
	 * */
	public int getSubchunk2Size()
	{
		return subchunk2Size;
	}
	
}
