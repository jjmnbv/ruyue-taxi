package com.szyciov.carservice.util.sendservice.sendmethod.log;

import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarForceSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarGrabSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarGrabSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarSystemSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpCarGrabSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpCarGrabSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpCarSystemSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpForceSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.taxi.ForceSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.taxi.GrabSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.taxi.GrabSingleSendMethodImp;
import org.slf4j.Logger;

/**
 * 派单日志
 * Created by LC on 2017/6/23.
 */
public class SendLogMessage
{

    /**
     * 派单主方法进入log
     * @param logger
     */
    public static void loginSendLog(Logger logger,Class cls,String orderno){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------开始派单流程",orderno);
    }


    /**
     * 即刻单主方法进入log
     * @param logger
     */
    public static void loginSend_UseNow(Logger logger,Class cls,String orderno){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------开始即刻单流程",orderno);
    }


    /**
     * 找到司机log
     * @param logger
     */
    public static void findDriver(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------找到司机个数【{}】",message);
    }

    /**
     * 即刻单主方法进入log
     * @param logger
     */
    public static void canPush2Driver(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------司机：【{}】"
            + "存在以下情况不可派单\n："
            + "当前单为即刻单：\n"
            + "1.存在未开始的即刻单或正在服务的订单\n"
            + "2.不在空闲状态\n"
            + "3.当前订单用车时间不在已存在预约单用车时间一小时之后\n"
            + "当前单为预约单：\n"
            + "1.当前订单的用车时间,不晚于服务中订单的预估时间的2倍\n"
            + "2.当前订单用车时间不在已存在的即刻单一个小时之后\n"
            + "3.当前订单的用车时间与已存在的预约单是同一天\n",message);
    }

    /**
     * 即刻单主方法进入log
     * @param logger
     */
    public static void sendOrder2Driver(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------推送限制人数:{},查询司机人数:{},已推送人数:{}",message);
    }


    /**
     * 静默推送log
     * @param logger
     */
    public static void onceSend4Reserve_SOW(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------进入静默推送，推送限制人数:{},查询司机人数:{},已推送人数:{}",message);
    }

    /**
     * 推送特殊司机log
     * @param logger
     * @param cls
     * @param strings
     */
    public static void sendOrder2SpecialDriver(Logger logger,Class cls,String[] message){
    	logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------进入特殊司机推送，查询司机人数:{},已推送人数:{}",message);
    }
    
    /**
     * 进行按距离排序日志
     * @param logger
     * @param cls
     * @param message
     */
    public static void optimalSortDriver(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------进行按距离排序",message);
    }


    /**
     * 进行按服务星级排序日志
     * @param logger
     * @param cls
     * @param message
     */
    public static void serverLevelSortDriver(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------进行按服务星级排序",message);
    }

    /**
     * 推送app日志
     * @param logger
     * @param cls
     * @param message
     */
    public static void pushAppMessage(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------发送app推送消息,司机电话：{}",message);
    }
    /**
     * 保存司机静默推送日志
     * @param logger
     * @param cls
     * @param message
     */
    public static void saveDriverMessage(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------保存静默推送redis数据,司机个数：{}",message);
    }




    /**
     * 派单结束
     * @param logger
     * @param cls
     * @param message
     */
    public static void sendEnd(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------未找到司机，派单结束！",message);
    }

    /**
     * 转人工
     * @param logger
     * @param cls
     * @param message
     */
    public static void toMantic(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------未找到司机，进入待人工派单",message);
    }

    /**
     * 发送待人工弹窗
     * @param logger
     * @param cls
     * @param message
     */
    public static void toRedisMessage(Logger logger,Class cls,String[] message){
        logger.warn(SendLogMessage.getOrderType(cls)+":订单编号-【{}】------发送待人工派单Redis消息",message);
    }



    /**
     * 预约单主方法进入log
     * @param logger
     */
    public static void loginSend_Reserve(Logger logger,Class cls,String orderno){
        logger.warn( SendLogMessage.getOrderType(cls)+":订单编号-【{}】------开始预约单流程",orderno);
    }


    /**
     * 预约单主方法进入log
     * @param logger
     */
    public static void send_Reserve_SOW(Logger logger,Class cls,String orderno,boolean sow){
        logger.warn( SendLogMessage.getOrderType(cls)+":订单编号-【{}】------开始"+((sow)?"强":"弱")+"调度流程",orderno);
    }



    /**
     * 返回派单模式
     * @param
     * @return
     */
    public static String getOrderType(Class cls){
        String typeName = "";
        if(cls.equals(GrabSingleSendMethodImp.class)){
            typeName = "【运管端】-【出租车】-【抢单模式】";
        }else if(cls.equals(ForceSendMethodImp.class)){
            typeName = "【运管端】-【出租车】-【强派模式】";
        }else if(cls.equals(GrabSendMethodImp.class)){
            typeName = "【运管端】-【出租车】-【抢派模式】";
        }else if(cls.equals(OpCarGrabSendMethodImp.class)){
            typeName = "【运管端】-【网约车】-【抢派模式】";
        }else if(cls.equals(OpForceSendMethodImp.class)){
            typeName = "【运管端】-【网约车】-【强派模式】";
        }else if(cls.equals(OpCarGrabSingleSendMethodImp.class)){
            typeName = "【运管端】-【网约车】-【抢单模式】";
        }else if(cls.equals(OpCarSystemSingleSendMethodImp.class)){
            typeName = "【运管端】-【网约车】-【纯人工模式】";
        }
        /****************租赁端******************************/
        else if(cls.equals(LeCarGrabSendMethodImp.class)){
            typeName = "【租赁端】-【网约车】-【抢派模式】";
        }else if(cls.equals(LeCarForceSendMethodImp.class)){
            typeName = "【租赁端】-【网约车】-【强派模式】";
        }else if(cls.equals(LeCarGrabSingleSendMethodImp.class)){
            typeName = "【租赁端】-【网约车】-【抢单模式】";
        }else if(cls.equals(LeCarSystemSingleSendMethodImp.class)){
            typeName = "【租赁端】-【网约车】-【纯人工模式】";
        }
        return typeName;
    }



}
 