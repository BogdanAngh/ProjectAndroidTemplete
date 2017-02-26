package org.jsoup.parser;

import android.support.v4.internal.view.SupportMenu;
import com.mp3download.zingmp3.C1569R;

enum TokeniserState {
    Data {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.emit(r.consume());
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    t.advanceTransition(CharacterReferenceInData);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.advanceTransition(TagOpen);
                case SupportMenu.USER_MASK /*65535*/:
                    t.emit(new EOF());
                default:
                    t.emit(r.consumeToAny('&', '<', TokeniserState.nullChar));
            }
        }
    },
    CharacterReferenceInData {
        void read(Tokeniser t, CharacterReader r) {
            char[] c = t.consumeCharacterReference(null, false);
            if (c == null) {
                t.emit('&');
            } else {
                t.emit(c);
            }
            t.transition(Data);
        }
    },
    Rcdata {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    t.advanceTransition(CharacterReferenceInRcdata);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.advanceTransition(RcdataLessthanSign);
                case SupportMenu.USER_MASK /*65535*/:
                    t.emit(new EOF());
                default:
                    t.emit(r.consumeToAny('&', '<', TokeniserState.nullChar));
            }
        }
    },
    CharacterReferenceInRcdata {
        void read(Tokeniser t, CharacterReader r) {
            char[] c = t.consumeCharacterReference(null, false);
            if (c == null) {
                t.emit('&');
            } else {
                t.emit(c);
            }
            t.transition(Rcdata);
        }
    },
    Rawtext {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.advanceTransition(RawtextLessthanSign);
                case SupportMenu.USER_MASK /*65535*/:
                    t.emit(new EOF());
                default:
                    t.emit(r.consumeToAny('<', TokeniserState.nullChar));
            }
        }
    },
    ScriptData {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.advanceTransition(ScriptDataLessthanSign);
                case SupportMenu.USER_MASK /*65535*/:
                    t.emit(new EOF());
                default:
                    t.emit(r.consumeToAny('<', TokeniserState.nullChar));
            }
        }
    },
    PLAINTEXT {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                case SupportMenu.USER_MASK /*65535*/:
                    t.emit(new EOF());
                default:
                    t.emit(r.consumeTo((char) TokeniserState.nullChar));
            }
        }
    },
    TagOpen {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case C1569R.styleable.AppCompatTheme_actionModeCopyDrawable /*33*/:
                    t.advanceTransition(MarkupDeclarationOpen);
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.advanceTransition(EndTagOpen);
                case C1569R.styleable.AppCompatTheme_editTextColor /*63*/:
                    t.advanceTransition(BogusComment);
                default:
                    if (r.matchesLetter()) {
                        t.createTagPending(true);
                        t.transition(TagName);
                        return;
                    }
                    t.error((TokeniserState) this);
                    t.emit('<');
                    t.transition(Data);
            }
        }
    },
    EndTagOpen {
        void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.emit("</");
                t.transition(Data);
            } else if (r.matchesLetter()) {
                t.createTagPending(false);
                t.transition(TagName);
            } else if (r.matches('>')) {
                t.error((TokeniserState) this);
                t.advanceTransition(Data);
            } else {
                t.error((TokeniserState) this);
                t.advanceTransition(BogusComment);
            }
        }
    },
    TagName {
        void read(Tokeniser t, CharacterReader r) {
            t.tagPending.appendTagName(r.consumeToAny('\t', '\n', '\r', '\f', ' ', '/', '>', TokeniserState.nullChar).toLowerCase());
            switch (r.consume()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.tagPending.appendTagName(TokeniserState.replacementStr);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BeforeAttributeName);
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.transition(SelfClosingStartTag);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
            }
        }
    },
    RcdataLessthanSign {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(RCDATAEndTagOpen);
            } else if (!r.matchesLetter() || r.containsIgnoreCase("</" + t.appropriateEndTagName())) {
                t.emit("<");
                t.transition(Rcdata);
            } else {
                t.tagPending = new EndTag(t.appropriateEndTagName());
                t.emitTagPending();
                r.unconsume();
                t.transition(Data);
            }
        }
    },
    RCDATAEndTagOpen {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.tagPending.appendTagName(Character.toLowerCase(r.current()));
                t.dataBuffer.append(Character.toLowerCase(r.current()));
                t.advanceTransition(RCDATAEndTagName);
                return;
            }
            t.emit("</");
            t.transition(Rcdata);
        }
    },
    RCDATAEndTagName {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                String name = r.consumeLetterSequence();
                t.tagPending.appendTagName(name.toLowerCase());
                t.dataBuffer.append(name);
                return;
            }
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    if (t.isAppropriateEndTagToken()) {
                        t.transition(BeforeAttributeName);
                    } else {
                        anythingElse(t, r);
                    }
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    if (t.isAppropriateEndTagToken()) {
                        t.transition(SelfClosingStartTag);
                    } else {
                        anythingElse(t, r);
                    }
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    if (t.isAppropriateEndTagToken()) {
                        t.emitTagPending();
                        t.transition(Data);
                        return;
                    }
                    anythingElse(t, r);
                default:
                    anythingElse(t, r);
            }
        }

        private void anythingElse(Tokeniser t, CharacterReader r) {
            t.emit("</" + t.dataBuffer.toString());
            r.unconsume();
            t.transition(Rcdata);
        }
    },
    RawtextLessthanSign {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(RawtextEndTagOpen);
                return;
            }
            t.emit('<');
            t.transition(Rawtext);
        }
    },
    RawtextEndTagOpen {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.transition(RawtextEndTagName);
                return;
            }
            t.emit("</");
            t.transition(Rawtext);
        }
    },
    RawtextEndTagName {
        void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataEndTag(t, r, Rawtext);
        }
    },
    ScriptDataLessthanSign {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.AppCompatTheme_actionModeCopyDrawable /*33*/:
                    t.emit("<!");
                    t.transition(ScriptDataEscapeStart);
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.createTempBuffer();
                    t.transition(ScriptDataEndTagOpen);
                default:
                    t.emit("<");
                    r.unconsume();
                    t.transition(ScriptData);
            }
        }
    },
    ScriptDataEndTagOpen {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.transition(ScriptDataEndTagName);
                return;
            }
            t.emit("</");
            t.transition(ScriptData);
        }
    },
    ScriptDataEndTagName {
        void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataEndTag(t, r, ScriptData);
        }
    },
    ScriptDataEscapeStart {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matches('-')) {
                t.emit('-');
                t.advanceTransition(ScriptDataEscapeStartDash);
                return;
            }
            t.transition(ScriptData);
        }
    },
    ScriptDataEscapeStartDash {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matches('-')) {
                t.emit('-');
                t.advanceTransition(ScriptDataEscapedDashDash);
                return;
            }
            t.transition(ScriptData);
        }
    },
    ScriptDataEscaped {
        void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(Data);
                return;
            }
            switch (r.current()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.emit('-');
                    t.advanceTransition(ScriptDataEscapedDash);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.advanceTransition(ScriptDataEscapedLessthanSign);
                default:
                    t.emit(r.consumeToAny('-', '<', TokeniserState.nullChar));
            }
        }
    },
    ScriptDataEscapedDash {
        void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(Data);
                return;
            }
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataEscaped);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.emit(c);
                    t.transition(ScriptDataEscapedDashDash);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.transition(ScriptDataEscapedLessthanSign);
                default:
                    t.emit(c);
                    t.transition(ScriptDataEscaped);
            }
        }
    },
    ScriptDataEscapedDashDash {
        void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(Data);
                return;
            }
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataEscaped);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.emit(c);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.transition(ScriptDataEscapedLessthanSign);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emit(c);
                    t.transition(ScriptData);
                default:
                    t.emit(c);
                    t.transition(ScriptDataEscaped);
            }
        }
    },
    ScriptDataEscapedLessthanSign {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTempBuffer();
                t.dataBuffer.append(Character.toLowerCase(r.current()));
                t.emit("<" + r.current());
                t.advanceTransition(ScriptDataDoubleEscapeStart);
            } else if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(ScriptDataEscapedEndTagOpen);
            } else {
                t.emit('<');
                t.transition(ScriptDataEscaped);
            }
        }
    },
    ScriptDataEscapedEndTagOpen {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.tagPending.appendTagName(Character.toLowerCase(r.current()));
                t.dataBuffer.append(r.current());
                t.advanceTransition(ScriptDataEscapedEndTagName);
                return;
            }
            t.emit("</");
            t.transition(ScriptDataEscaped);
        }
    },
    ScriptDataEscapedEndTagName {
        void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataEndTag(t, r, ScriptDataEscaped);
        }
    },
    ScriptDataDoubleEscapeStart {
        void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataDoubleEscaped, ScriptDataEscaped);
        }
    },
    ScriptDataDoubleEscaped {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.current();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.emit(c);
                    t.advanceTransition(ScriptDataDoubleEscapedDash);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.emit(c);
                    t.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    t.emit(r.consumeToAny('-', '<', TokeniserState.nullChar));
            }
        }
    },
    ScriptDataDoubleEscapedDash {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataDoubleEscaped);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscapedDashDash);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscapedLessthanSign);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscaped);
            }
        }
    },
    ScriptDataDoubleEscapedDashDash {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataDoubleEscaped);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.emit(c);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscapedLessthanSign);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emit(c);
                    t.transition(ScriptData);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscaped);
            }
        }
    },
    ScriptDataDoubleEscapedLessthanSign {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matches('/')) {
                t.emit('/');
                t.createTempBuffer();
                t.advanceTransition(ScriptDataDoubleEscapeEnd);
                return;
            }
            t.transition(ScriptDataDoubleEscaped);
        }
    },
    ScriptDataDoubleEscapeEnd {
        void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataEscaped, ScriptDataDoubleEscaped);
        }
    },
    BeforeAttributeName {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(AttributeName);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                case C1569R.styleable.AppCompatTheme_popupMenuStyle /*61*/:
                    t.error((TokeniserState) this);
                    t.tagPending.newAttribute();
                    t.tagPending.appendAttributeName(c);
                    t.transition(AttributeName);
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.transition(SelfClosingStartTag);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(AttributeName);
            }
        }
    },
    AttributeName {
        void read(Tokeniser t, CharacterReader r) {
            t.tagPending.appendAttributeName(r.consumeToAny('\t', '\n', '\r', '\f', ' ', '/', '=', '>', TokeniserState.nullChar, '\"', '\'', '<').toLowerCase());
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeName((char) TokeniserState.replacementChar);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(AfterAttributeName);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeName(c);
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.transition(SelfClosingStartTag);
                case C1569R.styleable.AppCompatTheme_popupMenuStyle /*61*/:
                    t.transition(BeforeAttributeValue);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
            }
        }
    },
    AfterAttributeName {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeName((char) TokeniserState.replacementChar);
                    t.transition(AttributeName);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                    t.error((TokeniserState) this);
                    t.tagPending.newAttribute();
                    t.tagPending.appendAttributeName(c);
                    t.transition(AttributeName);
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.transition(SelfClosingStartTag);
                case C1569R.styleable.AppCompatTheme_popupMenuStyle /*61*/:
                    t.transition(BeforeAttributeValue);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(AttributeName);
            }
        }
    },
    BeforeAttributeValue {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                    t.transition(AttributeValue_unquoted);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.transition(AttributeValue_doubleQuoted);
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    r.unconsume();
                    t.transition(AttributeValue_unquoted);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.transition(AttributeValue_singleQuoted);
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                case C1569R.styleable.AppCompatTheme_popupMenuStyle /*61*/:
                case C1569R.styleable.AppCompatTheme_alertDialogTheme /*96*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue(c);
                    t.transition(AttributeValue_unquoted);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    r.unconsume();
                    t.transition(AttributeValue_unquoted);
            }
        }
    },
    AttributeValue_doubleQuoted {
        void read(Tokeniser t, CharacterReader r) {
            String value = r.consumeToAny('\"', '&', TokeniserState.nullChar);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            }
            switch (r.consume()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.transition(AfterAttributeValue_quoted);
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    char[] ref = t.consumeCharacterReference(Character.valueOf('\"'), true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                    } else {
                        t.tagPending.appendAttributeValue('&');
                    }
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
            }
        }
    },
    AttributeValue_singleQuoted {
        void read(Tokeniser t, CharacterReader r) {
            String value = r.consumeToAny('\'', '&', TokeniserState.nullChar);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            }
            switch (r.consume()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    char[] ref = t.consumeCharacterReference(Character.valueOf('\''), true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                    } else {
                        t.tagPending.appendAttributeValue('&');
                    }
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.transition(AfterAttributeValue_quoted);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
            }
        }
    },
    AttributeValue_unquoted {
        void read(Tokeniser t, CharacterReader r) {
            String value = r.consumeToAny('\t', '\n', '\r', '\f', ' ', '&', '>', TokeniserState.nullChar, '\"', '\'', '<', '=', '`');
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            }
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BeforeAttributeName);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                case C1569R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
                case C1569R.styleable.AppCompatTheme_popupMenuStyle /*61*/:
                case C1569R.styleable.AppCompatTheme_alertDialogTheme /*96*/:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue(c);
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    char[] ref = t.consumeCharacterReference(Character.valueOf('>'), true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                    } else {
                        t.tagPending.appendAttributeValue('&');
                    }
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
            }
        }
    },
    AfterAttributeValue_quoted {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BeforeAttributeName);
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.transition(SelfClosingStartTag);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    r.unconsume();
                    t.transition(BeforeAttributeName);
            }
        }
    },
    SelfClosingStartTag {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.tagPending.selfClosing = true;
                    t.emitTagPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.transition(BeforeAttributeName);
            }
        }
    },
    BogusComment {
        void read(Tokeniser t, CharacterReader r) {
            r.unconsume();
            Token comment = new Comment();
            comment.bogus = true;
            comment.data.append(r.consumeTo('>'));
            t.emit(comment);
            t.advanceTransition(Data);
        }
    },
    MarkupDeclarationOpen {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchConsume("--")) {
                t.createCommentPending();
                t.transition(CommentStart);
            } else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
                t.transition(Doctype);
            } else if (r.matchConsume("[CDATA[")) {
                t.transition(CdataSection);
            } else {
                t.error((TokeniserState) this);
                t.advanceTransition(BogusComment);
            }
        }
    },
    CommentStart {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append(TokeniserState.replacementChar);
                    t.transition(Comment);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.transition(CommentStartDash);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.emitCommentPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                default:
                    t.commentPending.data.append(c);
                    t.transition(Comment);
            }
        }
    },
    CommentStartDash {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append(TokeniserState.replacementChar);
                    t.transition(Comment);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.transition(CommentStartDash);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.emitCommentPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                default:
                    t.commentPending.data.append(c);
                    t.transition(Comment);
            }
        }
    },
    Comment {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.commentPending.data.append(TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.advanceTransition(CommentEndDash);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                default:
                    t.commentPending.data.append(r.consumeToAny('-', TokeniserState.nullChar));
            }
        }
    },
    CommentEndDash {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append('-').append(TokeniserState.replacementChar);
                    t.transition(Comment);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.transition(CommentEnd);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                default:
                    t.commentPending.data.append('-').append(c);
                    t.transition(Comment);
            }
        }
    },
    CommentEnd {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append("--").append(TokeniserState.replacementChar);
                    t.transition(Comment);
                case C1569R.styleable.AppCompatTheme_actionModeCopyDrawable /*33*/:
                    t.error((TokeniserState) this);
                    t.transition(CommentEndBang);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append('-');
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitCommentPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append("--").append(c);
                    t.transition(Comment);
            }
        }
    },
    CommentEndBang {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append("--!").append(TokeniserState.replacementChar);
                    t.transition(Comment);
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    t.commentPending.data.append("--!");
                    t.transition(CommentEndDash);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitCommentPending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                default:
                    t.commentPending.data.append("--!").append(c);
                    t.transition(Comment);
            }
        }
    },
    Doctype {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BeforeDoctypeName);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.createDoctypePending();
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.transition(BeforeDoctypeName);
            }
        }
    },
    BeforeDoctypeName {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createDoctypePending();
                t.transition(DoctypeName);
                return;
            }
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.name.append(TokeniserState.replacementChar);
                    t.transition(DoctypeName);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.createDoctypePending();
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.createDoctypePending();
                    t.doctypePending.name.append(c);
                    t.transition(DoctypeName);
            }
        }
    },
    DoctypeName {
        void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.doctypePending.name.append(r.consumeLetterSequence().toLowerCase());
                return;
            }
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.name.append(TokeniserState.replacementChar);
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(AfterDoctypeName);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.doctypePending.name.append(c);
            }
        }
    },
    AfterDoctypeName {
        void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(Data);
            } else if (r.matchesAny('\t', '\n', '\r', '\f', ' ')) {
                r.advance();
            } else if (r.matches('>')) {
                t.emitDoctypePending();
                t.advanceTransition(Data);
            } else if (r.matchConsumeIgnoreCase("PUBLIC")) {
                t.transition(AfterDoctypePublicKeyword);
            } else if (r.matchConsumeIgnoreCase("SYSTEM")) {
                t.transition(AfterDoctypeSystemKeyword);
            } else {
                t.error((TokeniserState) this);
                t.doctypePending.forceQuirks = true;
                t.advanceTransition(BogusDoctype);
            }
        }
    },
    AfterDoctypePublicKeyword {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BeforeDoctypePublicIdentifier);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypePublicIdentifier_doubleQuoted);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypePublicIdentifier_singleQuoted);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
            }
        }
    },
    BeforeDoctypePublicIdentifier {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.transition(DoctypePublicIdentifier_doubleQuoted);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.transition(DoctypePublicIdentifier_singleQuoted);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
            }
        }
    },
    DoctypePublicIdentifier_doubleQuoted {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.publicIdentifier.append(TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.transition(AfterDoctypePublicIdentifier);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.doctypePending.publicIdentifier.append(c);
            }
        }
    },
    DoctypePublicIdentifier_singleQuoted {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.publicIdentifier.append(TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.transition(AfterDoctypePublicIdentifier);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.doctypePending.publicIdentifier.append(c);
            }
        }
    },
    AfterDoctypePublicIdentifier {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BetweenDoctypePublicAndSystemIdentifiers);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
            }
        }
    },
    BetweenDoctypePublicAndSystemIdentifiers {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
            }
        }
    },
    AfterDoctypeSystemKeyword {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BeforeDoctypeSystemIdentifier);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
            }
        }
    },
    BeforeDoctypeSystemIdentifier {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
            }
        }
    },
    DoctypeSystemIdentifier_doubleQuoted {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.systemIdentifier.append(TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    t.transition(AfterDoctypeSystemIdentifier);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.doctypePending.systemIdentifier.append(c);
            }
        }
    },
    DoctypeSystemIdentifier_singleQuoted {
        void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.systemIdentifier.append(TokeniserState.replacementChar);
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    t.transition(AfterDoctypeSystemIdentifier);
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.doctypePending.systemIdentifier.append(c);
            }
        }
    },
    AfterDoctypeSystemIdentifier {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
                    t.error((TokeniserState) this);
                    t.transition(BogusDoctype);
            }
        }
    },
    BogusDoctype {
        void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitDoctypePending();
                    t.transition(Data);
                case SupportMenu.USER_MASK /*65535*/:
                    t.emitDoctypePending();
                    t.transition(Data);
                default:
            }
        }
    },
    CdataSection {
        void read(Tokeniser t, CharacterReader r) {
            t.emit(r.consumeTo("]]>"));
            r.matchConsume("]]>");
            t.transition(Data);
        }
    };
    
    private static final char eof = '\uffff';
    private static final char nullChar = '\u0000';
    private static final char replacementChar = '\ufffd';
    private static final String replacementStr;

    abstract void read(Tokeniser tokeniser, CharacterReader characterReader);

    static {
        replacementStr = String.valueOf(replacementChar);
    }

    private static final void handleDataEndTag(Tokeniser t, CharacterReader r, TokeniserState elseTransition) {
        if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name.toLowerCase());
            t.dataBuffer.append(name);
            return;
        }
        boolean needsExitTransition = false;
        if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
            char c = r.consume();
            switch (c) {
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    t.transition(BeforeAttributeName);
                    break;
                case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    t.transition(SelfClosingStartTag);
                    break;
                case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                    t.emitTagPending();
                    t.transition(Data);
                    break;
                default:
                    t.dataBuffer.append(c);
                    needsExitTransition = true;
                    break;
            }
        }
        needsExitTransition = true;
        if (needsExitTransition) {
            t.emit("</" + t.dataBuffer.toString());
            t.transition(elseTransition);
        }
    }

    private static final void handleDataDoubleEscapeTag(Tokeniser t, CharacterReader r, TokeniserState primary, TokeniserState fallback) {
        if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.dataBuffer.append(name.toLowerCase());
            t.emit(name);
            return;
        }
        char c = r.consume();
        switch (c) {
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
            case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
            case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
            case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
            case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
            case C1569R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
            case C1569R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
                if (t.dataBuffer.toString().equals("script")) {
                    t.transition(primary);
                } else {
                    t.transition(fallback);
                }
                t.emit(c);
            default:
                r.unconsume();
                t.transition(fallback);
        }
    }
}
