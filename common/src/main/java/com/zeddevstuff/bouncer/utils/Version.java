package com.zeddevstuff.bouncer.utils;

public class Version
{
    public final String source;
    public final int major;
    public final int minor;
    public final int patch;
    public final int build;

    public Version(String source) {
        this.source = source;
        // That's just in case
        source = source.replace("v", "");
        // That's for fabric
        int index = source.indexOf("+");
        if(index != -1) {
            source = source.substring(0, index);
        }
        String[] parts = source.split("\\.");
        if(parts.length > 0) {
            major = Integer.parseInt(parts[0]);
        } else {
            major = -1;
        }
        if(parts.length > 1) {
            minor = Integer.parseInt(parts[1]);
        } else {
            minor = -1;
        }
        if(parts.length > 2) {
            patch = Integer.parseInt(parts[2]);
        } else {
            patch = -1;
        }
        if(parts.length > 3) {
            build = Integer.parseInt(parts[3]);
        } else {
            build = -1;
        }
    }
    public Version(int major, int minor, int patch, int build) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.build = build;
        this.source = major + "." + minor + "." + patch + "." + build;
    }
    public boolean isGreaterThan(Version other) {
        if(major > other.major) {
            return true;
        }
        if(major < other.major) {
            return false;
        }
        if(minor > other.minor) {
            return true;
        }
        if(minor < other.minor) {
            return false;
        }
        if(patch > other.patch) {
            return true;
        }
        if(patch < other.patch) {
            return false;
        }
        return build > other.build;
    }
    public boolean isLessThan(Version other) {
        if(major < other.major) {
            return true;
        }
        if(major > other.major) {
            return false;
        }
        if(minor < other.minor) {
            return true;
        }
        if(minor > other.minor) {
            return false;
        }
        if(patch < other.patch) {
            return true;
        }
        if(patch > other.patch) {
            return false;
        }
        return build < other.build;
    }
    public boolean isEqualTo(Version other) {
        return major == other.major && minor == other.minor && patch == other.patch && build == other.build;
    }

    @Override
    public String toString() {
        String result = "v";
        if(major != -1) {
            result += major;
        }
        if(minor != -1) {
            result += "." + minor;
        }
        if(patch != -1) {
            result += "." + patch;
        }
        if(build != -1) {
            result += "." + build;
        }
        return result;
    }
}
