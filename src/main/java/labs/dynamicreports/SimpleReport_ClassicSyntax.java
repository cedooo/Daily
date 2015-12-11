package labs.dynamicreports;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;

 

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

import net.sf.dynamicreports.report.builder.DynamicReports;

import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.PercentageColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;

import net.sf.dynamicreports.report.builder.component.Components;

import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.SplitType;

import net.sf.dynamicreports.report.datasource.DRDataSource;

import net.sf.dynamicreports.report.exception.DRException;

import net.sf.jasperreports.engine.JRDataSource;


import static net.sf.dynamicreports.report.builder.DynamicReports.*;


public class SimpleReport_ClassicSyntax {

    

   public SimpleReport_ClassicSyntax() {

      build();

   }

    

   private void build() {

      try {

         JasperReportBuilder report = DynamicReports.report();

         report.addColumn(Columns.column("Item", "item", DataTypes.stringType()));

         report.addColumn(Columns.column("Quantity", "quantity", DataTypes.integerType()));

         report.addColumn(Columns.column("Unit price", "unitprice", DataTypes.bigDecimalType()));

         report.addTitle(Components.text("Sets the title band split type" +
"SplitType.PREVENT - prevents the band from splitting" +
"SplitType.STRETCH - the band can be split, but never within its declared height." +
"SplitType.IMMEDIATE - the band can be split ， 中文字体")).setTitleSplitType(SplitType.STRETCH);

         report.pageHeader(cmp.text ("This is a page header band")).addPageFooter(Components.pageXofY());

         
         report.setDataSource(createDataSource());
         report.detailHeader(cmp.text(new Date()));
         report.detail(cmp.text("a record"));
         report.detailFooter(cmp.text("a record end!"));

         report.setBackgroundStyle(stl.style().setBackgroundColor(Color.LIGHT_GRAY));
         report.background(cmp.text(new Date()));
         
         TextColumnBuilder<Integer> quantityColumn = col.column("Quantity", "quantity", type.integerType()); 
         PercentageColumnBuilder quantityPercColumn = col.percentageColumn("Quantity [%]", quantityColumn);
         report.addColumn(quantityPercColumn);
         
         
         
         
         
         report.show();

      } catch (DRException e) {

         e.printStackTrace();

      }

   }

    

   private JRDataSource createDataSource() {

      DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");

      dataSource.add("Notebook", 1, new BigDecimal(500));

      dataSource.add("DVD", 5, new BigDecimal(30));

      dataSource.add("DVD", 1, new BigDecimal(28));

      dataSource.add("DVD", 5, new BigDecimal(32));

      dataSource.add("Book", 3, new BigDecimal(11));

      dataSource.add("Book", 1, new BigDecimal(15));

      dataSource.add("Book", 5, new BigDecimal(10));

      dataSource.add("Book", 8, new BigDecimal(9));

      return dataSource;

   }

    

   public static void main(String[] args) {

      new SimpleReport_ClassicSyntax();

   }

}
