<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
	<xsl:variable name="pageTitle" select="//RS_genreic_transaction_search/pageTitle"/>
	<xsl:variable name="APP_ROOT" select="//RS_genreic_transaction_search/appRoot"/>
	<xsl:variable name="evenRowColor">#CCFFCC</xsl:variable>
	<xsl:variable name="oddRowColor">#FFFFFF</xsl:variable>

    <xsl:template match="/">
		<html>
		  <head>
		    <title>
		       <xsl:value-of select="$pageTitle"/>
		    </title>
		    <meta http-equiv="Pragma" content="no-cache"/>
		    <meta http-equiv="Expires" content="-1"/>  
		    <link rel="STYLESHEET" type="text/css">
		       <xsl:attribute name="href">
				  <xsl:value-of select="$APP_ROOT"/>
				  <xsl:text>/css/RMT2Table.css</xsl:text>
	  		   </xsl:attribute>
		    </link>
			<link rel="STYLESHEET" type="text/css">
			   <xsl:attribute name="href">
				  <xsl:value-of select="$APP_ROOT"/>
				  <xsl:text>/css/RMT2General.css</xsl:text>
	  		   </xsl:attribute>
	  		</link>
			<script Language="JavaScript">
			   <xsl:attribute name="src">
				    <xsl:value-of select="$APP_ROOT"/>
				    <xsl:text>/js/RMT2General.js</xsl:text>
	  		  </xsl:attribute>
			</script>
			<script Language="JavaScript">
			   <xsl:attribute name="src">
				  <xsl:value-of select="$APP_ROOT"/>
				  <xsl:text>/js/RMT2Menu.js</xsl:text>
	  		   </xsl:attribute>
			</script>
			<script>
			    function initPage() {
						return;	
			 		}
			</script>
		  </head>
		   
		  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()"> 
		       <form name="SearchForm" method="POST">
		          <xsl:attribute name="action">
				     			<xsl:value-of select="$APP_ROOT"/>
				     			<xsl:text>/unsecureRequestProcessor/GenericTransaction.Search</xsl:text>
	  		      </xsl:attribute>
	  		      
			     <h3>
			       <strong>
			          <xsl:value-of select="$pageTitle"/>
			       </strong>
			     </h3>
				 
				 <!-- Display common selection criteria items  -->
				 <font size="3" style="color:blue">Selection Criteria - Default criteria includes transactions of current date and 30 days before</font>
				 <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:75%; height:55px">
				    <xsl:apply-templates select="//RS_genreic_transaction_search/search_criteria"/>
 		     </div>

				 <!-- Display Search buttons -->  
				 <table>
				    <tr>
				   	   <td colspan="2">
								  <input name="search" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)"/>
								  <input name="newsearch" type="button" value="Clear" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)"/>
								</td>
					  </tr>
				  </table>
				  <br/>
			  
			  <font size="3" style="color:blue">Search Results</font>
			  <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:75%; height:425px; overflow:auto">
				  <xsl:apply-templates select="//RS_genreic_transaction_search/transactions"/>	  
			  </div>
		
				<!-- Display any messgaes -->
				<table>
						<tr>
						  <td colspan="4">
						     <font color="red">
						         <xsl:value-of select="//RS_genreic_transaction_search/message"/>
							 </font>
						  </td>
						</tr>
						<tr>
						  <td>
						     <font color="black">Total Items Found:</font>
						  </td>
						  <td colspan="3" align="left">
						     <font color="red">
						         <xsl:value-of select="//RS_genreic_transaction_search/transaction_count"/>
							 </font>
						  </td>
						</tr>
						<tr>
						  <td>
						     <font color="black">Total Amount:</font>
						  </td>
						  <td colspan="3" align="left">
						     <font color="red">
							     <xsl:value-of select="format-number(//RS_genreic_transaction_search/transaction_total, '$#,##0.00;($#,##0.00)')"/>
							 </font>
						  </td>
						</tr>
				</table>
			  <table>
			    <tr>
					<td colspan="2">
					  <input name="back" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)"/>
					</td>
				</tr>
			  </table>
			  	  
			 <input name="clientAction" type="hidden"/>
		   </form>
		 </body>
		</html>
    </xsl:template>
    
    <xsl:template match="search_criteria">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			   <td width="15%" class="clsTableFormHeader">Transaction Date:</td>
			   <td align="left" colspan="4">
		          <select name="qryRelOpXactDate1">
		             <xsl:call-template name="CondOpts">
				        <xsl:with-param name="selectedValue" select="qryRelOp_XactDate_1"/>
			         </xsl:call-template>
		          </select> 
				  <input type="text" name="qry_XactDate1">
				  	<xsl:attribute name="value">
					    <xsl:value-of select="qry_XactDate_1"/>
			  		</xsl:attribute>
			  	  </input>
				  &#160;<b>and</b>&#160;
				  <select name="qryRelOpXactDate2">
				     <xsl:call-template name="CondOpts">
				        <xsl:with-param name="selectedValue" select="qryRelOp_XactDate_2"/>
			       </xsl:call-template>
				  </select>
				  <input type="text" name="qry_XactDate2">
				  	<xsl:attribute name="value">
					    <xsl:value-of select="qry_XactDate_2"/>
			  		</xsl:attribute>
			  	  </input>
			   </td>
			   <td align="left">&#160;</td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Transaction Amount:</td>
			   <td align="left" colspan="4">
			      <select name="qryRelOpXactAmount1">
			         <xsl:call-template name="CondOpts">
				        <xsl:with-param name="selectedValue" select="qryRelOp_XactAmount_1"/>
			         </xsl:call-template>
			      </select> 
			      <input type="text" name="qry_XactAmount1">
			      	<xsl:attribute name="value">
					    <xsl:value-of select="qry_XactAmount_1"/>
			  		</xsl:attribute>
			  	  </input>
			      &#160;<b>and</b>&#160;
				  <select name="qryRelOpXactAmount2">
				     <xsl:call-template name="CondOpts">
				        <xsl:with-param name="selectedValue" select="qryRelOp_XactAmount_2"/>
			         </xsl:call-template>
				  </select> 
			      <input type="text" name="qry_XactAmount2">
			         <xsl:attribute name="value">
					    <xsl:value-of select="qry_XactAmount_2"/>
			  		 </xsl:attribute>
			  	  </input>
			   </td>
			   <td align="left">&#160;</td>
			</tr>	
			<tr>
			   <td class="clsTableFormHeader">Creditor/Customer Name:</td>
			   <td align="left" colspan="2">
				   <input type="text" name="BusinessNameTmp" size="50" maxLength="50">
				      <xsl:attribute name="value">
					       <xsl:value-of select="qry_BusinessName"/>
			  		  </xsl:attribute>
			  	 </input>
			   </td>
      </tr>
			<tr>
			   <td class="clsTableFormHeader">Source/Reason:</td>
			   <td align="left" colspan="2">
				   <input type="text" name="qry_XactReason" size="50" maxLength="50">
				      <xsl:attribute name="value">
					    <xsl:value-of select="qry_XactReason"/>
			  		 </xsl:attribute>
			  	   </input>
			   </td>
			   <td align="left"><strong>Advanced Search Options:&#160;&#160;&#160;</strong>
			      <xsl:variable name="serachOpts" select="qry_XactReason_ADVSRCHOPTS"/>
			      Begins With
			      <input type="radio" name="qry_XactReason_ADVSRCHOPTS" value="begin">
				      <xsl:if test="$serachOpts = 'begin' or $serachOpts = ''">
				         <xsl:attribute name="checked"/>
				      </xsl:if>
			      </input>&#160;&#160;&#160;&#160;
				  
					  Ends With
					  <input type="radio" name="qry_XactReason_ADVSRCHOPTS" value="end">
						  <xsl:if test="$serachOpts = 'end'">
					         <xsl:attribute name="checked"/>
					      </xsl:if>
				    </input>&#160;&#160;&#160;&#160;
					  
					  Contains
					  <input type="radio" name="qry_XactReason_ADVSRCHOPTS" value="contain">
						  <xsl:if test="$serachOpts = 'contain'">
					         <xsl:attribute name="checked"/>
					      </xsl:if>&#160;&#160;&#160;&#160;
				    </input>
				      
					  Exact Match
					  <input type="radio" name="qry_XactReason_ADVSRCHOPTS" value="exact">
						  <xsl:if test="$serachOpts = 'exact'">
					         <xsl:attribute name="checked"/>
					      </xsl:if>
			      </input>
			   </td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Confirmation Number:</td>
			   <td align="left" colspan="4">
				   <input type="text" name="qry_ConfirmNo" size="40" maxLength="20">
				      <xsl:attribute name="value">
					    <xsl:value-of select="qry_ConfirmNo"/>
			  		 </xsl:attribute>
			  	   </input>
			   </td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Transaction Type:</td>
			   <td colspan="5" align="left">
				   <select id="qry_XactTypeId" name="qry_XactTypeId">
				     <option value=""></option>
				     <xsl:apply-templates select="//RS_genreic_transaction_search/transaction_types"/>
				   </select> 
			   </td>
			</tr>
			<tr>
			   <td class="clsTableFormHeader">Transaction Sub-Type:</td>
			   <td colspan="5" align="left">
				   <select id="qry_XactSubtypeId" name="qry_XactSubtypeId">
				     <option value=""></option>
				     <xsl:apply-templates select="//RS_genreic_transaction_search/transaction_sub_types"/>
				   </select> 
			   </td>
			</tr>
		 </table>    
    </xsl:template>
    
    <xsl:template match="transactions">
    	    <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
    			 <tr>
    				 <th width="3%" class="clsTableListHeader">&#160;</th>
    				 <th width="5%" class="clsTableListHeader">Id</th>
    				 <th width="5%" class="clsTableListHeader">&#160;</th>
    				 <th width="5%" class="clsTableListHeader">Trans. Date</th>
    				 <th width="10%" class="clsTableListHeader" style="text-align:right">Amount</th>
    				 <th width="2%" class="clsTableListHeader">&#160;</th>
    				 <th width="13%" class="clsTableListHeader" style="text-align:left">Trans. Type</th>				 
    				 <th width="13%" class="clsTableListHeader" style="text-align:left">Confirmation No.</th>
    				 <th width="20%" class="clsTableListHeader" style="text-align:left">Customer/Creditor Name</th>
    				 <th width="24%" class="clsTableListHeader" style="text-align:left">Trans. Source/Reason</th>
    			 </tr>
    			
    			 <xsl:for-each select="transaction">
    			    <tr>
    				   <xsl:attribute name="bgcolor">
    					   <xsl:if test="(position()-1) mod 2 = 0">
    					       <xsl:value-of select="$evenRowColor"/>
    					    </xsl:if>
    					    <xsl:if test="not((position()-1) mod 2 = 0)">
    					       <xsl:value-of select="$oddRowColor"/>
    					    </xsl:if>
    					</xsl:attribute>
    				 	<td align="center" class="clsTableListHeader"> 
    					</td>   
    					<td align="center" >
    					   <font size="2">
    							 <input type="hidden">
    							    <xsl:attribute name="name">
    							 	    <xsl:value-of select="concat('XactId', position()-1)"/>
    				 	      		</xsl:attribute>
    							 	<xsl:attribute name="value">
    							 	    <xsl:value-of select="xact_id"/>
    				 	      		</xsl:attribute>
    				 	      	 </input>
    							 <input type="hidden">
    							    <xsl:attribute name="name">
    							 	    <xsl:value-of select="concat('XactTypeId', position()-1)"/>
    				 	      		</xsl:attribute>
    							 	<xsl:attribute name="value">
    							 	    <xsl:value-of select="xact_type_id"/>
    				 	      		</xsl:attribute> 
    				 	      	 </input>
    							 <xsl:value-of select="xact_id"/>
    					   </font>
    					</td>   
    					
    					<!--  Set document id -->
    					<td align="center" >
    					   <xsl:if test="document_id > 0">
    					       <a>
    					          <xsl:attribute name="href">
					                  <xsl:value-of select="$APP_ROOT"/>
					                  <xsl:text>/DocumentViewer.jsp?contentId=</xsl:text>
					                  <xsl:value-of select="document_id"/>
	  		                      </xsl:attribute>
	  		                      <xsl:attribute name="target">
				                      <xsl:text>_blank</xsl:text>
	  		                      </xsl:attribute>
	  		                      <image>
	  		                          <xsl:attribute name="src">
						                  <xsl:value-of select="$APP_ROOT"/>
						                  <xsl:text>/images/camera2.png</xsl:text>
	  		                          </xsl:attribute>
		  		                      <xsl:attribute name="style">
					                      <xsl:text>border: none</xsl:text>
		  		                      </xsl:attribute>
			  		                  <xsl:attribute name="alt">
						                  <xsl:text>View original source document</xsl:text>
			  		                  </xsl:attribute>
	  		                      </image>
	  		                   </a>
    					    </xsl:if>
    					    <xsl:if test="document_id = 0">&#160;</xsl:if>
    					</td>   
    					          
    					<td align="center">
    					   <font size="2"> 
    					      <xsl:value-of select="xact_date"/>
    					   </font>
    					</td>
    					<td align="right">
    						<font size="2">
    						   <xsl:value-of select="format-number(xact_amount, '$#,##0.00;($#,##0.00)')"/>
    						</font>
    					</td>
    					<td align="right">&#160;</td>							 
    					<td align="left" >
    						<font size="2">
    						   <xsl:value-of select="xact_type_name"/>
    						</font>
    					</td>
    					<td align="left" >
    						<font size="2">
    						   <xsl:value-of select="confirm_no"/> 
    						</font>
    					</td>																
    					<td align="left" >
    						<font size="2">
    						   <xsl:value-of select="business_name"/>
    						</font>
    					</td>							
    					<td align="left" >
    						<font size="2">
    						   <xsl:value-of select="xact_reason"/>
    						</font>
    					</td>
    			    </tr>
    			 </xsl:for-each>
    			
    			 <xsl:variable name="totalRows" select="//RS_genreic_transaction_search/transaction_count"/>
    			 <xsl:if test="$totalRows &lt;= 0">
                   <tr>
                     <td colspan="9" align="center">Data Not Found</td>
                   </tr>			    
    			 </xsl:if>
    
    		</table>
    </xsl:template>
    
    <xsl:template name="CondOpts">
      <xsl:param name="selectedValue"/> 
         <option value="">
	         <xsl:if test="$selectedValue = ''">
		         <xsl:attribute name="selected"/>
	         </xsl:if>
         </option>
         
				 <option value="=">
					 	<xsl:if test="$selectedValue = '='">
				         <xsl:attribute name="selected"/>
		  	    </xsl:if>
			   </option>=       
		 		 
		 		 <option value="&lt;&gt;">
         	 <xsl:if test="$selectedValue = '&lt;&gt;'">
		         <xsl:attribute name="selected"/>
	         </xsl:if>
	       </option>&lt;&gt;
	       
         <option value="&gt;">
         	 <xsl:if test="$selectedValue = '&gt;'">
		         <xsl:attribute name="selected"/>
	         </xsl:if>
	       </option>&gt;
		          
         <option value="&gt;=">
            <xsl:if test="$selectedValue = '&gt;='">
		         <xsl:attribute name="selected"/>
	         </xsl:if>
	       </option>&gt;= 
         
         <option value="&lt;">
            <xsl:if test="$selectedValue = '&lt;'">
		       <xsl:attribute name="selected"/>
	        </xsl:if>
	       </option>&lt;   
         
         <option value="&lt;=">
            <xsl:if test="$selectedValue = '&lt;='">
		       <xsl:attribute name="selected"/>
	        </xsl:if>
	       </option>&lt;=   
    </xsl:template>
    
    <xsl:template match="transaction_types">
	  <xsl:variable name="selectedXactType" select="//RS_genreic_transaction_search/search_criteria/qry_XactTypeId"/>
      <xsl:for-each select="item">
         <xsl:variable name="curXactType" select="xact_type_id"/>
         <option>
            <xsl:attribute name="value">
               <xsl:value-of select="xact_type_id"/>
            </xsl:attribute>
		 	<xsl:if test="$selectedXactType = $curXactType">
		       <xsl:attribute name="selected"/>
	        </xsl:if>
		 </option>
		 <xsl:value-of select="description"/>
      </xsl:for-each>
    </xsl:template>

	<xsl:template match="transaction_sub_types">
	  <xsl:variable name="selectedXactType" select="//RS_genreic_transaction_search/search_criteria/qry_XactSubtypeId"/>
      <xsl:for-each select="item">
         <xsl:variable name="curXactType" select="xact_type_id"/>
         <option>
            <xsl:attribute name="value">
               <xsl:value-of select="xact_type_id"/>
            </xsl:attribute>
		 	<xsl:if test="$selectedXactType = $curXactType">
		       <xsl:attribute name="selected"/>
	        </xsl:if>
		 </option>
		 <xsl:value-of select="description"/>
      </xsl:for-each>
    </xsl:template>

<xsl:template match="xact_id">
	
</xsl:template>
</xsl:stylesheet>