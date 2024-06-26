package com.jiang.filter;

import java.util.*;

/**
 * 敏感词过滤器
 */
public class TextFilter {
    /**
     * 储存敏感词的map结构，其中每个键是字符，值也是一个map结构，用来储存下一个字符
     */
    private Map<Object,Object> sensitiveWordsMap;

    /**
     * 静态常量，用来标识敏感词的结束
     */
    private static final String END_FLAG="end";

    /**
     * 创建敏感词Map
     * @param sensitiveWords
     */
    private void initSensitiveWordsMap(Set<String> sensitiveWords){
        // 非空验证
        if(sensitiveWords==null||sensitiveWords.isEmpty()){
            throw new IllegalArgumentException("Senditive words must not be empty!");
        }
        sensitiveWordsMap=new HashMap<>(sensitiveWords.size());
        String currentWord;
        Map<Object,Object> currentMap;
        Map<Object,Object> subMap;
        Iterator<String> iterator = sensitiveWords.iterator();
        while (iterator.hasNext()){
            currentWord=iterator.next();
            //敏感词长度必须大于等于2，长度小于2为无效敏感词
            if(currentWord==null||currentWord.trim().length()<2){
                continue;
            }
            currentMap=sensitiveWordsMap;

            // 遍历每一个敏感词，将敏感词以char的形式嵌套存入map中
            for(int i=0;i<currentWord.length();i++){
                char c=currentWord.charAt(i);
                subMap=(Map<Object, Object>) currentMap.get(c);
                if(subMap==null){
                    subMap=new HashMap<>();
                    currentMap.put(c,subMap);
                    currentMap=subMap;
                }else {
                    currentMap= subMap;
                }
                if(i==currentWord.length()-1){
                    //如果是最后一个字符，则put一个结束标志，这里只需要保存key就行了，value为null可以节省空间。
                    //如果不是最后一个字符，则不需要存这个结束标志，同样也是为了节省空间。
                    currentMap.put(END_FLAG,null);
                }
            }
        }
    }

    /**
     * 获取敏感词
     * @param text
     * @param matchType
     * @return
     */
    public Set<String> getSensitiveWords(String text,MatchType matchType){
        // 非空判断
        if(text==null||text.trim().length()==0){
            throw new IllegalArgumentException("The input text must not be empty.");
        }
        Set<String> sensitiveWords=new HashSet<>();
        for(int i=0;i<text.length();i++){
            int sensitiveWordLength = getSensitiveWordLength(text, i, matchType);
            if(sensitiveWordLength>0){
                String sensitiveWord = text.substring(i, i + sensitiveWordLength);
                sensitiveWords.add(sensitiveWord);
                if(matchType==MatchType.MIN_MATCH){
                    break;
                }
                i=i+sensitiveWordLength-1;
            }
        }
        return sensitiveWords;
    }

    /**
     * 获取敏感词长度
     * @param text
     * @param startIndex
     * @param matchType
     * @return
     */
    public int getSensitiveWordLength(String text,int startIndex,MatchType matchType){
        if(text==null||text.trim().length()==0){
            throw new IllegalArgumentException("The input text must not be empty.");
        }
        char currentChar;
        Map<Object,Object> currentMap=sensitiveWordsMap;
        int wordLength=0;
        boolean endFlag=false;
        for(int i=startIndex;i<text.length();i++){
            currentChar=text.charAt(i);
            Map<Object,Object> subMap=(Map<Object,Object>) currentMap.get(currentChar);
            if(subMap==null){
                break;
            }else {
                wordLength++;
                if(subMap.containsKey(END_FLAG)){
                    endFlag=true;
                    if(matchType==MatchType.MIN_MATCH){
                        break;
                    }else {
                        currentMap=subMap;
                    }
                }else {
                    currentMap=subMap;
                }
            }
        }
        if(!endFlag){
            wordLength=0;
        }
        return wordLength;
    }

    


    public static Set<String> newHashSet(String... strings) {
        HashSet<String> set = new HashSet<String>();

        for (String s : strings) {
            set.add(s);
        }
        return set;
    }

    public static String blockSensitiveWords(String text){
        if(text == "" || text.isEmpty()){
            System.err.println("未接受到消息");
            return "";
        }

        Set<String> sensitiveWords = newHashSet("傻逼", "shit","原神","启动","操你妈","cnm","傻子");
        TextFilter textFilter=new TextFilter();
        textFilter.initSensitiveWordsMap(sensitiveWords);


        for (String word : textFilter.getSensitiveWords(text,MatchType.MAX_MATCH)) {
            text = text.replaceAll(word, "**");
        }
        return  text;
    }

    public enum MatchType {

        MIN_MATCH("最小匹配规则"),MAX_MATCH("最大匹配规则");

        String desc;

        MatchType(String desc) {
            this.desc = desc;
        }
    }
}