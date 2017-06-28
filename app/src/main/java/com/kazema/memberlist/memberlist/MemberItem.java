package com.kazema.memberlist.memberlist;

/**
 * A member item representing a piece of content.
 */
public class MemberItem {
    public final long id;
    public final String content;

    public MemberItem(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}