/*
 * Created on Thu Jun 06 2024
 *
 * The MIT License (MIT)
 * Copyright (c) 2024 Samuele Camilletti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.unicam.cs.formula1.api.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import it.unicam.cs.formula1.api.Track;

/**
 * Defines a loader from file for a track
 */
public interface TrackLoader {
   
   /**
    * Parse a string into a track
    * @param s
    * @return a Track
    * @throws IOException
    */
   Track parse(String s) throws IOException;


   /**
    * Generate a Track given a file
    * @param path
    * @return
    * @throws IOException
    */
   default Track parse(Path path) throws IOException {
      return parse(Files.readString(path));
   }


}
