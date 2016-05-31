///////////////////////////////////////////////////////////////////////////////
//PROJECT:       Micro-Manager
//SUBSYSTEM:     mmstudio
//-----------------------------------------------------------------------------
//               Definition of the Acquisition Protocol to be executed
//               by the acquisition engine
//
// AUTHOR:       Arthur Edelstein, Nenad Amodaj
//
// COPYRIGHT:    University of California, San Francisco, 2013
//
// LICENSE:      This file is distributed under the BSD license.
//               License text is included with the source distribution.
//
//               This file is distributed in the hope that it will be useful,
//               but WITHOUT ANY WARRANTY; without even the implied warranty
//               of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//
//               IN NO EVENT SHALL THE COPYRIGHT OWNER OR
//               CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
//               INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
//

package org.micromanager;

import java.util.ArrayList;

import org.micromanager.ChannelSpec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * SequenceSettings objects contain the parameters describing how to run a
 * single acquisition. Various methods of the AcquisitionManager will consume
 * or generate SequenceSettings, and you can create your own to configure your
 * custom acquisitions.
 */
public class SequenceSettings {
   // version ID for the sequence settings
   public static final double Version = 1.1;

   // acquisition protocol
   /**
    * number of frames
    */
   public int numFrames = 1;
   /**
    * frame interval
    */
   public double intervalMs = 0.0;
   /**
    * Whether or not to use custom time intervals. Do not set this to true
    * if customIntervalsMs is null!
    */
   public boolean useCustomIntervals;
   /**
    * sequence of custom intervals or null
    */
   public ArrayList<Double> customIntervalsMs = null;
   /**
    * an array of ChannelSpec settings (one for each channel)
    */
   public ArrayList<ChannelSpec> channels = new ArrayList<ChannelSpec>();
   /**
    * slice Z coordinates
    */
   public ArrayList<Double> slices = new ArrayList<Double>();
   /**
    * are Z coordinates relative or absolute
    */
   public boolean relativeZSlice = false;
   /**
    * slice coordinate changes first
    */
   public boolean slicesFirst = false;
   /**
    * frame coordinate changes first
    */
   public boolean timeFirst = false;
   /**
    * do we keep shutter open during slice changes
    */
   public boolean keepShutterOpenSlices = false;
   /**
    * do we keep shutter open channel changes
    */
   public boolean keepShutterOpenChannels = false;
   /**
    * are we going to run autofocus before acquiring each position/frame
    */
   public boolean useAutofocus = false;
   /**
    * how many autofocus opportunities to skip
    */
   public int skipAutofocusCount = 0;
   /**
    * save to disk?
    */
   public boolean save = false;
   /**
    * root directory name
    */
   public String root = null;
   /**
    * acquisition name
    */
   public String prefix = null;
   /**
    * referent z position for relative moves
    */
   public double zReference = 0.0;
   /**
    * comment text
    */
   public String comment = "";
   /**
    * which configuration group is used to define channels
    */
   public String channelGroup = "";
   /**
    * true if we want to have multiple positions
    */
   public boolean usePositionList = false;
   /**
    * Minimum camera timeout, in ms, for sequence acquisitions
    * (actual timeout depends on exposure time and other factors)
    */
   public int cameraTimeout = 20000;
   /**
    * Whether or not to display images generated by the acquisition.
    */
   public boolean shouldDisplayImages = true;

   /**
    * Create a copy of this SequenceSettings. All parameters will be copied,
    * with new objects being created as necessary (i.e. this is a deep copy).
    * @return Copy of this SequenceSettings.
    */
   public SequenceSettings copy() {
      SequenceSettings result = new SequenceSettings();
      result.cameraTimeout = cameraTimeout;
      result.channelGroup = channelGroup;
      result.channels = channels == null ? null : new ArrayList<ChannelSpec>(channels);
      result.comment = comment;
      result.customIntervalsMs = customIntervalsMs == null ? null : new ArrayList<Double>(customIntervalsMs);
      result.intervalMs = intervalMs;
      result.keepShutterOpenSlices = keepShutterOpenSlices;
      result.keepShutterOpenChannels = keepShutterOpenChannels;
      result.numFrames = numFrames;
      result.prefix = prefix;
      result.relativeZSlice = relativeZSlice;
      result.root = root;
      result.save = save;
      result.shouldDisplayImages = shouldDisplayImages;
      result.skipAutofocusCount = skipAutofocusCount;
      result.slices = slices == null ? null : new ArrayList<Double>(slices);
      result.slicesFirst = slicesFirst;
      result.timeFirst = timeFirst;
      result.useAutofocus = useAutofocus;
      result.useCustomIntervals = useCustomIntervals;
      result.usePositionList = usePositionList;
      result.zReference = zReference;
      return result;
   }

   public static String toJSONStream(SequenceSettings settings) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      return gson.toJson(settings);
   }

   public static SequenceSettings fromJSONStream(String stream) {
      Gson gson = new Gson();
      return gson.fromJson(stream, SequenceSettings.class);
   }
}
