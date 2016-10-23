package com.zlebank.zplatform.task.enums;

/**
 * 
  * @ClassName: BiztypeEnum
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午8:05:54
  *
 */
public enum BiztypeEnum {
	NM000201("000201"),
	NM000202("000201"),
	NM000203("000201"),
	NM000204("000201"),
	NM000205("000201"),
	NM000206("000201"),
	NM000207("000201"),
	NM000208("000201"),
	NM000209("000201"),
	NM000210("000201"),
	UNKNOW("");
	private String code;

	private BiztypeEnum(String code){
	    this.code = code;
	}

	public static BiztypeEnum fromValue(String value) {
	    for(BiztypeEnum status:values()){
	        if(status.code.equals(value)){
	            return status;
	        }
	    }
	    return UNKNOW;
	}

	public String getCode(){
	    return code;
	}
}
