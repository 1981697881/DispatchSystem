package com.dispatch.system.entity;

import java.util.List;

/**
 * 楼栋树，三级
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/22
 */
public class BuildingTreeBean extends BaseBean {

    /**
     * data : {"buildings":[{"code":"9c7cd917c67847edb1d7f8434b3ce31f","name":"测试花园","parentCode":null,"sort":"1","createTime":1590395664853,"updateTime":1590395664853,"children":[{"code":"ec7cd917c67adb1d7fs843d4b3ae31cf","name":"C区","parentCode":"9c7cd917c67847edb1d7f8434b3ce31f","sort":"1","createTime":1590395664853,"updateTime":1590395664853,"children":[{"code":"ec7cd917c67adb1d7fs843d4b3ae31cf","name":"C区","parentCode":"9c7cd917c67847edb1d7f8434b3ce31f","sort":"1","createTime":1590395664853,"updateTime":1590395664853}]}]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean  {
        private List<BuildingsBean> buildings;

        public List<BuildingsBean> getBuildings() {
            return buildings;
        }

        public void setBuildings(List<BuildingsBean> buildings) {
            this.buildings = buildings;
        }

        public static class BuildingsBean extends BuildingTreeChildBean  {
            /**
             * code : 9c7cd917c67847edb1d7f8434b3ce31f
             * name : 测试花园
             * parentCode : null
             * sort : 1
             * createTime : 1590395664853
             * updateTime : 1590395664853
             * children : [{"code":"ec7cd917c67adb1d7fs843d4b3ae31cf","name":"C区","parentCode":"9c7cd917c67847edb1d7f8434b3ce31f","sort":"1","createTime":1590395664853,"updateTime":1590395664853,"children":[{"code":"ec7cd917c67adb1d7fs843d4b3ae31cf","name":"C区","parentCode":"9c7cd917c67847edb1d7f8434b3ce31f","sort":"1","createTime":1590395664853,"updateTime":1590395664853}]}]
             */

            private List<ChildrenBeanX> children;

            public List<ChildrenBeanX> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBeanX> children) {
                this.children = children;
            }

            public static class ChildrenBeanX extends BuildingTreeChildBean  {
                /**
                 * code : ec7cd917c67adb1d7fs843d4b3ae31cf
                 * name : C区
                 * parentCode : 9c7cd917c67847edb1d7f8434b3ce31f
                 * sort : 1
                 * createTime : 1590395664853
                 * updateTime : 1590395664853
                 * children : [{"code":"ec7cd917c67adb1d7fs843d4b3ae31cf","name":"C区","parentCode":"9c7cd917c67847edb1d7f8434b3ce31f","sort":"1","createTime":1590395664853,"updateTime":1590395664853}]
                 */

                private List<BuildingTreeChildBean> children;

                public List<BuildingTreeChildBean> getChildren() {
                    return children;
                }

                public void setChildren(List<BuildingTreeChildBean> children) {
                    this.children = children;
                }

            }
        }
    }
}
