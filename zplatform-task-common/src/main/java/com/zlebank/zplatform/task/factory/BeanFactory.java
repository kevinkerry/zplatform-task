package com.zlebank.zplatform.task.factory;

import com.zlebank.zplatform.task.bean.NotifyBean;
import com.zlebank.zplatform.task.enums.BiztypeEnum;
import com.zlebank.zplatform.task.enums.BusinessEnum;

/**
  * @ClassName: BeanFactory
  * @Description: TODO
  * @author guojia
  * @date 2016年10月22日 下午8:42:53
  *
  */
public interface BeanFactory {

	/**
	 * 
	  * getBean
	  * @Title: getBean
	  * @Description: TODO
	  * @param biztypeEnum
	  * @return    参数
	  * @return Serializable    返回类型
	  * @throws
	 */
	public NotifyBean getBean(String txnseqno,BiztypeEnum biztypeEnum);
	
	/**
	 * 
	  * getInsteadPayBean
	  * @Title: getInsteadPayBean
	  * @Description: TODO
	  * @param txnseqno
	  * @param biztypeEnum
	  * @param businessEnum
	  * @return    参数
	  * @return NotifyBean    返回类型
	  * @throws
	 */
	public NotifyBean getInsteadPayBean(String txnseqno,BusinessEnum businessEnum);
}
