package ${vo.pkg}
{

    /**
    *名称: ${vo.clazzDesc}
    * @author 自动生成请勿修改
    *
    */
    public class ${vo.clazz}<#if vo.parentClazz?exists> extends ${vo.parentClazz} </#if>
    {
        public function ${vo.clazz}()
        {
        }
    <#list vo.attributes as e>

		<#if e.nameDesc ? exists>
        /**
        *  ${e.nameDesc} 
        */
		</#if>
        <#if e.type="java.lang.String">
        public String ${e.name};
		public String get${e.name}(){
			return this.${e.name};
		}
		public void set${e.name}(String ${e.name}){
			return this.${e.name};
		}
        <#elseif e.type="string">
        public String ${e.name};
		public String get${e.name?cap_first}(){
			return this.${e.name};
		}
		public void set${e.name?cap_first}(String ${e.name}){
			return this.${e.name};
		}
        <#else>
        public var ${e.name}:${e.type};
        </#if>
    </#list>

    }
}