package com.shribak.board.utils;

/**
 * The {@code ProviderFactory} class creates providers for work with
 * different formats of files.
 *
 * @author Dmitrii Shribak
 */
public class ProviderFactory {

    /**
     * Returns a provider depending on the extension of the file.
     *
     * @param extension
     * @return returns certain provider
     */
    public FileProvider getProvider(String extension) {
        FileProvider provider = null;

        if (extension.equalsIgnoreCase("json")) {
            provider = new JsonProvider();
        } else if(extension.equalsIgnoreCase("xml")) {
            provider = new XmlProvider();
        }

        return provider;
    }
}
