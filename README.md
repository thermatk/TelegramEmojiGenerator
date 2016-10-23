# TelegramEmojiGenerator
Code that helps to generate better emoji assets for the Telegram's Android client.

### Results are in workfiles/ready/

Very dirty code, but who cares in this case?

## Idea behind:
1. Get latest EmojiData.java(TMessagesProj/src/main/java/org/telegram/messenger/EmojiData.java)
1. Add fixEmoji method from TMessagesProj/src/main/java/org/telegram/messenger/Emoji.java
1. Add my method which outputs the emoji list to an external file, since I couldn't find a reliable way to get codepoints for emoji 4.0 in Java, and also because they are actually not writen down in a really standard way... 
1. To use the twitter's js lib next step is the small html file emojiConverter.html where you past the output of step 2, convert it to codepoints and copypaste it to another file.
1. There are some 5 special cases of still wrongly formatted emojis here, for whatever reason, fix them manually by searching for closest names in the current twemoji set(in all cases just delete the unnecessary fe0f, but it's placed randomly)
1. Run the Main.java file, which has all possible workarounds there. It uses the EmojiData.java, converted codepoints from step 5 
  * Generates twemoji assets, the only full set currently, using the twemoji folder with pngs
  * Generates Google Noto assets, which lacks some pngs in the repo, so had to add them from the font. In the end, for some flags Google doesn't have an Emoji yet, so fallback is Twemoji
  * Generates EmojiOne assets, but it's not 4.0 standard compliant, so twemoji is the fallback replacement

