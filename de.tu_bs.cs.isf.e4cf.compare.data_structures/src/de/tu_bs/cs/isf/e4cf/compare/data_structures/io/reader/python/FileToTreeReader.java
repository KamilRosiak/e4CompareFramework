package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.python;

import jep.Interpreter;
import jep.SharedInterpreter;

public class FileToTreeReader {
	
	
	public String getTreeFromFileMocked(String path) {
		return "{\r\n" + 
				"    \"_type\": \"Module\",\r\n" + 
				"    \"body\": [\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"ImportFrom\",\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"end_col_offset\": 32,\r\n" + 
				"            \"end_lineno\": 1,\r\n" + 
				"            \"level\": 0,\r\n" + 
				"            \"lineno\": 1,\r\n" + 
				"            \"module\": \"commands.util\",\r\n" + 
				"            \"names\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"alias\",\r\n" + 
				"                    \"asname\": null,\r\n" + 
				"                    \"col_offset\": 26,\r\n" + 
				"                    \"end_col_offset\": 32,\r\n" + 
				"                    \"end_lineno\": 1,\r\n" + 
				"                    \"lineno\": 1,\r\n" + 
				"                    \"name\": \"is_osx\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"ImportFrom\",\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"end_col_offset\": 36,\r\n" + 
				"            \"end_lineno\": 2,\r\n" + 
				"            \"level\": 0,\r\n" + 
				"            \"lineno\": 2,\r\n" + 
				"            \"module\": \"commands.util\",\r\n" + 
				"            \"names\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"alias\",\r\n" + 
				"                    \"asname\": null,\r\n" + 
				"                    \"col_offset\": 26,\r\n" + 
				"                    \"end_col_offset\": 36,\r\n" + 
				"                    \"end_lineno\": 2,\r\n" + 
				"                    \"lineno\": 2,\r\n" + 
				"                    \"name\": \"is_windows\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"ImportFrom\",\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"end_col_offset\": 32,\r\n" + 
				"            \"end_lineno\": 3,\r\n" + 
				"            \"level\": 0,\r\n" + 
				"            \"lineno\": 3,\r\n" + 
				"            \"module\": \"commands.util\",\r\n" + 
				"            \"names\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"alias\",\r\n" + 
				"                    \"asname\": null,\r\n" + 
				"                    \"col_offset\": 26,\r\n" + 
				"                    \"end_col_offset\": 32,\r\n" + 
				"                    \"end_lineno\": 3,\r\n" + 
				"                    \"lineno\": 3,\r\n" + 
				"                    \"name\": \"is_bsd\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"Import\",\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"end_col_offset\": 9,\r\n" + 
				"            \"end_lineno\": 4,\r\n" + 
				"            \"lineno\": 4,\r\n" + 
				"            \"names\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"alias\",\r\n" + 
				"                    \"asname\": null,\r\n" + 
				"                    \"col_offset\": 7,\r\n" + 
				"                    \"end_col_offset\": 9,\r\n" + 
				"                    \"end_lineno\": 4,\r\n" + 
				"                    \"lineno\": 4,\r\n" + 
				"                    \"name\": \"os\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"Import\",\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"end_col_offset\": 16,\r\n" + 
				"            \"end_lineno\": 5,\r\n" + 
				"            \"lineno\": 5,\r\n" + 
				"            \"names\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"alias\",\r\n" + 
				"                    \"asname\": null,\r\n" + 
				"                    \"col_offset\": 7,\r\n" + 
				"                    \"end_col_offset\": 16,\r\n" + 
				"                    \"end_lineno\": 5,\r\n" + 
				"                    \"lineno\": 5,\r\n" + 
				"                    \"name\": \"sysconfig\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"FunctionDef\",\r\n" + 
				"            \"args\": {\r\n" + 
				"                \"_type\": \"arguments\",\r\n" + 
				"                \"args\": [],\r\n" + 
				"                \"defaults\": [],\r\n" + 
				"                \"kw_defaults\": [],\r\n" + 
				"                \"kwarg\": null,\r\n" + 
				"                \"kwonlyargs\": [],\r\n" + 
				"                \"posonlyargs\": [],\r\n" + 
				"                \"vararg\": null\r\n" + 
				"            },\r\n" + 
				"            \"body\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Expr\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 7,\r\n" + 
				"                    \"end_lineno\": 13,\r\n" + 
				"                    \"lineno\": 9,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Constant\",\r\n" + 
				"                        \"col_offset\": 4,\r\n" + 
				"                        \"end_col_offset\": 7,\r\n" + 
				"                        \"end_lineno\": 13,\r\n" + 
				"                        \"kind\": null,\r\n" + 
				"                        \"lineno\": 9,\r\n" + 
				"                        \"n\": \"\\n    Get the shared library names for embedding jep.\\n\\n    See python-config\\n    \",\r\n" + 
				"                        \"s\": \"\\n    Get the shared library names for embedding jep.\\n\\n    See python-config\\n    \",\r\n" + 
				"                        \"value\": \"\\n    Get the shared library names for embedding jep.\\n\\n    See python-config\\n    \"\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Assign\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 43,\r\n" + 
				"                    \"end_lineno\": 14,\r\n" + 
				"                    \"lineno\": 14,\r\n" + 
				"                    \"targets\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 4,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Store\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 5,\r\n" + 
				"                            \"end_lineno\": 14,\r\n" + 
				"                            \"id\": \"v\",\r\n" + 
				"                            \"lineno\": 14\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"type_comment\": null,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 33,\r\n" + 
				"                                \"end_col_offset\": 42,\r\n" + 
				"                                \"end_lineno\": 14,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 14,\r\n" + 
				"                                \"n\": \"VERSION\",\r\n" + 
				"                                \"s\": \"VERSION\",\r\n" + 
				"                                \"value\": \"VERSION\"\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 8,\r\n" + 
				"                        \"end_col_offset\": 43,\r\n" + 
				"                        \"end_lineno\": 14,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"get_config_var\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 32,\r\n" + 
				"                            \"end_lineno\": 14,\r\n" + 
				"                            \"lineno\": 14,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 8,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 17,\r\n" + 
				"                                \"end_lineno\": 14,\r\n" + 
				"                                \"id\": \"sysconfig\",\r\n" + 
				"                                \"lineno\": 14\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 14\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Assign\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 47,\r\n" + 
				"                    \"end_lineno\": 15,\r\n" + 
				"                    \"lineno\": 15,\r\n" + 
				"                    \"targets\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 4,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Store\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 7,\r\n" + 
				"                            \"end_lineno\": 15,\r\n" + 
				"                            \"id\": \"ldv\",\r\n" + 
				"                            \"lineno\": 15\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"type_comment\": null,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 35,\r\n" + 
				"                                \"end_col_offset\": 46,\r\n" + 
				"                                \"end_lineno\": 15,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 15,\r\n" + 
				"                                \"n\": \"LDVERSION\",\r\n" + 
				"                                \"s\": \"LDVERSION\",\r\n" + 
				"                                \"value\": \"LDVERSION\"\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 10,\r\n" + 
				"                        \"end_col_offset\": 47,\r\n" + 
				"                        \"end_lineno\": 15,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"get_config_var\",\r\n" + 
				"                            \"col_offset\": 10,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 34,\r\n" + 
				"                            \"end_lineno\": 15,\r\n" + 
				"                            \"lineno\": 15,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 10,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 19,\r\n" + 
				"                                \"end_lineno\": 15,\r\n" + 
				"                                \"id\": \"sysconfig\",\r\n" + 
				"                                \"lineno\": 15\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 15\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Assign\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 15,\r\n" + 
				"                            \"end_lineno\": 17,\r\n" + 
				"                            \"lineno\": 17,\r\n" + 
				"                            \"targets\": [\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 8,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Store\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 9,\r\n" + 
				"                                    \"end_lineno\": 17,\r\n" + 
				"                                    \"id\": \"v\",\r\n" + 
				"                                    \"lineno\": 17\r\n" + 
				"                                }\r\n" + 
				"                            ],\r\n" + 
				"                            \"type_comment\": null,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 12,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 15,\r\n" + 
				"                                \"end_lineno\": 17,\r\n" + 
				"                                \"id\": \"ldv\",\r\n" + 
				"                                \"lineno\": 17\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 15,\r\n" + 
				"                    \"end_lineno\": 17,\r\n" + 
				"                    \"lineno\": 16,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"Name\",\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"ctx\": {\r\n" + 
				"                            \"_type\": \"Load\"\r\n" + 
				"                        },\r\n" + 
				"                        \"end_col_offset\": 10,\r\n" + 
				"                        \"end_lineno\": 16,\r\n" + 
				"                        \"id\": \"ldv\",\r\n" + 
				"                        \"lineno\": 16\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Assign\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 25,\r\n" + 
				"                    \"end_lineno\": 18,\r\n" + 
				"                    \"lineno\": 18,\r\n" + 
				"                    \"targets\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 4,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Store\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 8,\r\n" + 
				"                            \"end_lineno\": 18,\r\n" + 
				"                            \"id\": \"libs\",\r\n" + 
				"                            \"lineno\": 18\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"type_comment\": null,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"List\",\r\n" + 
				"                        \"col_offset\": 11,\r\n" + 
				"                        \"ctx\": {\r\n" + 
				"                            \"_type\": \"Load\"\r\n" + 
				"                        },\r\n" + 
				"                        \"elts\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"BinOp\",\r\n" + 
				"                                \"col_offset\": 12,\r\n" + 
				"                                \"end_col_offset\": 24,\r\n" + 
				"                                \"end_lineno\": 18,\r\n" + 
				"                                \"left\": {\r\n" + 
				"                                    \"_type\": \"Constant\",\r\n" + 
				"                                    \"col_offset\": 12,\r\n" + 
				"                                    \"end_col_offset\": 20,\r\n" + 
				"                                    \"end_lineno\": 18,\r\n" + 
				"                                    \"kind\": null,\r\n" + 
				"                                    \"lineno\": 18,\r\n" + 
				"                                    \"n\": \"python\",\r\n" + 
				"                                    \"s\": \"python\",\r\n" + 
				"                                    \"value\": \"python\"\r\n" + 
				"                                },\r\n" + 
				"                                \"lineno\": 18,\r\n" + 
				"                                \"op\": {\r\n" + 
				"                                    \"_type\": \"Add\"\r\n" + 
				"                                },\r\n" + 
				"                                \"right\": {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 23,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 24,\r\n" + 
				"                                    \"end_lineno\": 18,\r\n" + 
				"                                    \"id\": \"v\",\r\n" + 
				"                                    \"lineno\": 18\r\n" + 
				"                                }\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"end_col_offset\": 25,\r\n" + 
				"                        \"end_lineno\": 18,\r\n" + 
				"                        \"lineno\": 18\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Expr\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 25,\r\n" + 
				"                            \"end_lineno\": 20,\r\n" + 
				"                            \"lineno\": 20,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Call\",\r\n" + 
				"                                \"args\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Constant\",\r\n" + 
				"                                        \"col_offset\": 20,\r\n" + 
				"                                        \"end_col_offset\": 24,\r\n" + 
				"                                        \"end_lineno\": 20,\r\n" + 
				"                                        \"kind\": null,\r\n" + 
				"                                        \"lineno\": 20,\r\n" + 
				"                                        \"n\": \"dl\",\r\n" + 
				"                                        \"s\": \"dl\",\r\n" + 
				"                                        \"value\": \"dl\"\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"col_offset\": 8,\r\n" + 
				"                                \"end_col_offset\": 25,\r\n" + 
				"                                \"end_lineno\": 20,\r\n" + 
				"                                \"func\": {\r\n" + 
				"                                    \"_type\": \"Attribute\",\r\n" + 
				"                                    \"attr\": \"append\",\r\n" + 
				"                                    \"col_offset\": 8,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 19,\r\n" + 
				"                                    \"end_lineno\": 20,\r\n" + 
				"                                    \"lineno\": 20,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 8,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 12,\r\n" + 
				"                                        \"end_lineno\": 20,\r\n" + 
				"                                        \"id\": \"libs\",\r\n" + 
				"                                        \"lineno\": 20\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"keywords\": [],\r\n" + 
				"                                \"lineno\": 20\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 25,\r\n" + 
				"                    \"end_lineno\": 20,\r\n" + 
				"                    \"lineno\": 19,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"BoolOp\",\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"end_col_offset\": 40,\r\n" + 
				"                        \"end_lineno\": 19,\r\n" + 
				"                        \"lineno\": 19,\r\n" + 
				"                        \"op\": {\r\n" + 
				"                            \"_type\": \"And\"\r\n" + 
				"                        },\r\n" + 
				"                        \"values\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"UnaryOp\",\r\n" + 
				"                                \"col_offset\": 7,\r\n" + 
				"                                \"end_col_offset\": 23,\r\n" + 
				"                                \"end_lineno\": 19,\r\n" + 
				"                                \"lineno\": 19,\r\n" + 
				"                                \"op\": {\r\n" + 
				"                                    \"_type\": \"Not\"\r\n" + 
				"                                },\r\n" + 
				"                                \"operand\": {\r\n" + 
				"                                    \"_type\": \"Call\",\r\n" + 
				"                                    \"args\": [],\r\n" + 
				"                                    \"col_offset\": 11,\r\n" + 
				"                                    \"end_col_offset\": 23,\r\n" + 
				"                                    \"end_lineno\": 19,\r\n" + 
				"                                    \"func\": {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 11,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 21,\r\n" + 
				"                                        \"end_lineno\": 19,\r\n" + 
				"                                        \"id\": \"is_windows\",\r\n" + 
				"                                        \"lineno\": 19\r\n" + 
				"                                    },\r\n" + 
				"                                    \"keywords\": [],\r\n" + 
				"                                    \"lineno\": 19\r\n" + 
				"                                }\r\n" + 
				"                            },\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"UnaryOp\",\r\n" + 
				"                                \"col_offset\": 28,\r\n" + 
				"                                \"end_col_offset\": 40,\r\n" + 
				"                                \"end_lineno\": 19,\r\n" + 
				"                                \"lineno\": 19,\r\n" + 
				"                                \"op\": {\r\n" + 
				"                                    \"_type\": \"Not\"\r\n" + 
				"                                },\r\n" + 
				"                                \"operand\": {\r\n" + 
				"                                    \"_type\": \"Call\",\r\n" + 
				"                                    \"args\": [],\r\n" + 
				"                                    \"col_offset\": 32,\r\n" + 
				"                                    \"end_col_offset\": 40,\r\n" + 
				"                                    \"end_lineno\": 19,\r\n" + 
				"                                    \"func\": {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 32,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 38,\r\n" + 
				"                                        \"end_lineno\": 19,\r\n" + 
				"                                        \"id\": \"is_bsd\",\r\n" + 
				"                                        \"lineno\": 19\r\n" + 
				"                                    },\r\n" + 
				"                                    \"keywords\": [],\r\n" + 
				"                                    \"lineno\": 19\r\n" + 
				"                                }\r\n" + 
				"                            }\r\n" + 
				"                        ]\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Return\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 15,\r\n" + 
				"                    \"end_lineno\": 21,\r\n" + 
				"                    \"lineno\": 21,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Name\",\r\n" + 
				"                        \"col_offset\": 11,\r\n" + 
				"                        \"ctx\": {\r\n" + 
				"                            \"_type\": \"Load\"\r\n" + 
				"                        },\r\n" + 
				"                        \"end_col_offset\": 15,\r\n" + 
				"                        \"end_lineno\": 21,\r\n" + 
				"                        \"id\": \"libs\",\r\n" + 
				"                        \"lineno\": 21\r\n" + 
				"                    }\r\n" + 
				"                }\r\n" + 
				"            ],\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"decorator_list\": [],\r\n" + 
				"            \"end_col_offset\": 15,\r\n" + 
				"            \"end_lineno\": 21,\r\n" + 
				"            \"lineno\": 8,\r\n" + 
				"            \"name\": \"get_python_libs\",\r\n" + 
				"            \"returns\": null,\r\n" + 
				"            \"type_comment\": null\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"FunctionDef\",\r\n" + 
				"            \"args\": {\r\n" + 
				"                \"_type\": \"arguments\",\r\n" + 
				"                \"args\": [],\r\n" + 
				"                \"defaults\": [],\r\n" + 
				"                \"kw_defaults\": [],\r\n" + 
				"                \"kwarg\": null,\r\n" + 
				"                \"kwonlyargs\": [],\r\n" + 
				"                \"posonlyargs\": [],\r\n" + 
				"                \"vararg\": null\r\n" + 
				"            },\r\n" + 
				"            \"body\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Return\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 17,\r\n" + 
				"                            \"end_lineno\": 26,\r\n" + 
				"                            \"lineno\": 26,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"List\",\r\n" + 
				"                                \"col_offset\": 15,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"elts\": [],\r\n" + 
				"                                \"end_col_offset\": 17,\r\n" + 
				"                                \"end_lineno\": 26,\r\n" + 
				"                                \"lineno\": 26\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 17,\r\n" + 
				"                    \"end_lineno\": 26,\r\n" + 
				"                    \"lineno\": 25,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [],\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"end_col_offset\": 19,\r\n" + 
				"                        \"end_lineno\": 25,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 7,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 17,\r\n" + 
				"                            \"end_lineno\": 25,\r\n" + 
				"                            \"id\": \"is_windows\",\r\n" + 
				"                            \"lineno\": 25\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 25\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Return\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 63,\r\n" + 
				"                    \"end_lineno\": 27,\r\n" + 
				"                    \"lineno\": 27,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"List\",\r\n" + 
				"                        \"col_offset\": 11,\r\n" + 
				"                        \"ctx\": {\r\n" + 
				"                            \"_type\": \"Load\"\r\n" + 
				"                        },\r\n" + 
				"                        \"elts\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Call\",\r\n" + 
				"                                \"args\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Call\",\r\n" + 
				"                                        \"args\": [\r\n" + 
				"                                            {\r\n" + 
				"                                                \"_type\": \"Constant\",\r\n" + 
				"                                                \"col_offset\": 52,\r\n" + 
				"                                                \"end_col_offset\": 60,\r\n" + 
				"                                                \"end_lineno\": 27,\r\n" + 
				"                                                \"kind\": null,\r\n" + 
				"                                                \"lineno\": 27,\r\n" + 
				"                                                \"n\": \"LIBDIR\",\r\n" + 
				"                                                \"s\": \"LIBDIR\",\r\n" + 
				"                                                \"value\": \"LIBDIR\"\r\n" + 
				"                                            }\r\n" + 
				"                                        ],\r\n" + 
				"                                        \"col_offset\": 27,\r\n" + 
				"                                        \"end_col_offset\": 61,\r\n" + 
				"                                        \"end_lineno\": 27,\r\n" + 
				"                                        \"func\": {\r\n" + 
				"                                            \"_type\": \"Attribute\",\r\n" + 
				"                                            \"attr\": \"get_config_var\",\r\n" + 
				"                                            \"col_offset\": 27,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 51,\r\n" + 
				"                                            \"end_lineno\": 27,\r\n" + 
				"                                            \"lineno\": 27,\r\n" + 
				"                                            \"value\": {\r\n" + 
				"                                                \"_type\": \"Name\",\r\n" + 
				"                                                \"col_offset\": 27,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 36,\r\n" + 
				"                                                \"end_lineno\": 27,\r\n" + 
				"                                                \"id\": \"sysconfig\",\r\n" + 
				"                                                \"lineno\": 27\r\n" + 
				"                                            }\r\n" + 
				"                                        },\r\n" + 
				"                                        \"keywords\": [],\r\n" + 
				"                                        \"lineno\": 27\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"col_offset\": 12,\r\n" + 
				"                                \"end_col_offset\": 62,\r\n" + 
				"                                \"end_lineno\": 27,\r\n" + 
				"                                \"func\": {\r\n" + 
				"                                    \"_type\": \"Attribute\",\r\n" + 
				"                                    \"attr\": \"format\",\r\n" + 
				"                                    \"col_offset\": 12,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 26,\r\n" + 
				"                                    \"end_lineno\": 27,\r\n" + 
				"                                    \"lineno\": 27,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Constant\",\r\n" + 
				"                                        \"col_offset\": 12,\r\n" + 
				"                                        \"end_col_offset\": 19,\r\n" + 
				"                                        \"end_lineno\": 27,\r\n" + 
				"                                        \"kind\": null,\r\n" + 
				"                                        \"lineno\": 27,\r\n" + 
				"                                        \"n\": \"-L{0}\",\r\n" + 
				"                                        \"s\": \"-L{0}\",\r\n" + 
				"                                        \"value\": \"-L{0}\"\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"keywords\": [],\r\n" + 
				"                                \"lineno\": 27\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"end_col_offset\": 63,\r\n" + 
				"                        \"end_lineno\": 27,\r\n" + 
				"                        \"lineno\": 27\r\n" + 
				"                    }\r\n" + 
				"                }\r\n" + 
				"            ],\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"decorator_list\": [],\r\n" + 
				"            \"end_col_offset\": 63,\r\n" + 
				"            \"end_lineno\": 27,\r\n" + 
				"            \"lineno\": 24,\r\n" + 
				"            \"name\": \"get_python_linker_args\",\r\n" + 
				"            \"returns\": null,\r\n" + 
				"            \"type_comment\": null\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"FunctionDef\",\r\n" + 
				"            \"args\": {\r\n" + 
				"                \"_type\": \"arguments\",\r\n" + 
				"                \"args\": [],\r\n" + 
				"                \"defaults\": [],\r\n" + 
				"                \"kw_defaults\": [],\r\n" + 
				"                \"kwarg\": null,\r\n" + 
				"                \"kwonlyargs\": [],\r\n" + 
				"                \"posonlyargs\": [],\r\n" + 
				"                \"vararg\": null\r\n" + 
				"            },\r\n" + 
				"            \"body\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Return\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 65,\r\n" + 
				"                            \"end_lineno\": 32,\r\n" + 
				"                            \"lineno\": 32,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Call\",\r\n" + 
				"                                \"args\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Call\",\r\n" + 
				"                                        \"args\": [\r\n" + 
				"                                            {\r\n" + 
				"                                                \"_type\": \"Constant\",\r\n" + 
				"                                                \"col_offset\": 43,\r\n" + 
				"                                                \"end_col_offset\": 55,\r\n" + 
				"                                                \"end_lineno\": 32,\r\n" + 
				"                                                \"kind\": null,\r\n" + 
				"                                                \"lineno\": 32,\r\n" + 
				"                                                \"n\": \"PYTHONHOME\",\r\n" + 
				"                                                \"s\": \"PYTHONHOME\",\r\n" + 
				"                                                \"value\": \"PYTHONHOME\"\r\n" + 
				"                                            }\r\n" + 
				"                                        ],\r\n" + 
				"                                        \"col_offset\": 28,\r\n" + 
				"                                        \"end_col_offset\": 56,\r\n" + 
				"                                        \"end_lineno\": 32,\r\n" + 
				"                                        \"func\": {\r\n" + 
				"                                            \"_type\": \"Attribute\",\r\n" + 
				"                                            \"attr\": \"get\",\r\n" + 
				"                                            \"col_offset\": 28,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 42,\r\n" + 
				"                                            \"end_lineno\": 32,\r\n" + 
				"                                            \"lineno\": 32,\r\n" + 
				"                                            \"value\": {\r\n" + 
				"                                                \"_type\": \"Attribute\",\r\n" + 
				"                                                \"attr\": \"environ\",\r\n" + 
				"                                                \"col_offset\": 28,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 38,\r\n" + 
				"                                                \"end_lineno\": 32,\r\n" + 
				"                                                \"lineno\": 32,\r\n" + 
				"                                                \"value\": {\r\n" + 
				"                                                    \"_type\": \"Name\",\r\n" + 
				"                                                    \"col_offset\": 28,\r\n" + 
				"                                                    \"ctx\": {\r\n" + 
				"                                                        \"_type\": \"Load\"\r\n" + 
				"                                                    },\r\n" + 
				"                                                    \"end_col_offset\": 30,\r\n" + 
				"                                                    \"end_lineno\": 32,\r\n" + 
				"                                                    \"id\": \"os\",\r\n" + 
				"                                                    \"lineno\": 32\r\n" + 
				"                                                }\r\n" + 
				"                                            }\r\n" + 
				"                                        },\r\n" + 
				"                                        \"keywords\": [],\r\n" + 
				"                                        \"lineno\": 32\r\n" + 
				"                                    },\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Constant\",\r\n" + 
				"                                        \"col_offset\": 58,\r\n" + 
				"                                        \"end_col_offset\": 64,\r\n" + 
				"                                        \"end_lineno\": 32,\r\n" + 
				"                                        \"kind\": null,\r\n" + 
				"                                        \"lineno\": 32,\r\n" + 
				"                                        \"n\": \"DLLs\",\r\n" + 
				"                                        \"s\": \"DLLs\",\r\n" + 
				"                                        \"value\": \"DLLs\"\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"col_offset\": 15,\r\n" + 
				"                                \"end_col_offset\": 65,\r\n" + 
				"                                \"end_lineno\": 32,\r\n" + 
				"                                \"func\": {\r\n" + 
				"                                    \"_type\": \"Attribute\",\r\n" + 
				"                                    \"attr\": \"join\",\r\n" + 
				"                                    \"col_offset\": 15,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 27,\r\n" + 
				"                                    \"end_lineno\": 32,\r\n" + 
				"                                    \"lineno\": 32,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Attribute\",\r\n" + 
				"                                        \"attr\": \"path\",\r\n" + 
				"                                        \"col_offset\": 15,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 22,\r\n" + 
				"                                        \"end_lineno\": 32,\r\n" + 
				"                                        \"lineno\": 32,\r\n" + 
				"                                        \"value\": {\r\n" + 
				"                                            \"_type\": \"Name\",\r\n" + 
				"                                            \"col_offset\": 15,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 17,\r\n" + 
				"                                            \"end_lineno\": 32,\r\n" + 
				"                                            \"id\": \"os\",\r\n" + 
				"                                            \"lineno\": 32\r\n" + 
				"                                        }\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"keywords\": [],\r\n" + 
				"                                \"lineno\": 32\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 65,\r\n" + 
				"                    \"end_lineno\": 32,\r\n" + 
				"                    \"lineno\": 31,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [],\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"end_col_offset\": 19,\r\n" + 
				"                        \"end_lineno\": 31,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 7,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 17,\r\n" + 
				"                            \"end_lineno\": 31,\r\n" + 
				"                            \"id\": \"is_windows\",\r\n" + 
				"                            \"lineno\": 31\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 31\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Return\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 45,\r\n" + 
				"                    \"end_lineno\": 34,\r\n" + 
				"                    \"lineno\": 34,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 36,\r\n" + 
				"                                \"end_col_offset\": 44,\r\n" + 
				"                                \"end_lineno\": 34,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 34,\r\n" + 
				"                                \"n\": \"LIBDIR\",\r\n" + 
				"                                \"s\": \"LIBDIR\",\r\n" + 
				"                                \"value\": \"LIBDIR\"\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 11,\r\n" + 
				"                        \"end_col_offset\": 45,\r\n" + 
				"                        \"end_lineno\": 34,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"get_config_var\",\r\n" + 
				"                            \"col_offset\": 11,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 35,\r\n" + 
				"                            \"end_lineno\": 34,\r\n" + 
				"                            \"lineno\": 34,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 11,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 20,\r\n" + 
				"                                \"end_lineno\": 34,\r\n" + 
				"                                \"id\": \"sysconfig\",\r\n" + 
				"                                \"lineno\": 34\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 34\r\n" + 
				"                    }\r\n" + 
				"                }\r\n" + 
				"            ],\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"decorator_list\": [],\r\n" + 
				"            \"end_col_offset\": 45,\r\n" + 
				"            \"end_lineno\": 34,\r\n" + 
				"            \"lineno\": 30,\r\n" + 
				"            \"name\": \"get_python_lib_dir\",\r\n" + 
				"            \"returns\": null,\r\n" + 
				"            \"type_comment\": null\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"_type\": \"FunctionDef\",\r\n" + 
				"            \"args\": {\r\n" + 
				"                \"_type\": \"arguments\",\r\n" + 
				"                \"args\": [],\r\n" + 
				"                \"defaults\": [],\r\n" + 
				"                \"kw_defaults\": [],\r\n" + 
				"                \"kwarg\": null,\r\n" + 
				"                \"kwonlyargs\": [],\r\n" + 
				"                \"posonlyargs\": [],\r\n" + 
				"                \"vararg\": null\r\n" + 
				"            },\r\n" + 
				"            \"body\": [\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Expr\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 7,\r\n" + 
				"                    \"end_lineno\": 41,\r\n" + 
				"                    \"lineno\": 38,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Constant\",\r\n" + 
				"                        \"col_offset\": 4,\r\n" + 
				"                        \"end_col_offset\": 7,\r\n" + 
				"                        \"end_lineno\": 41,\r\n" + 
				"                        \"kind\": null,\r\n" + 
				"                        \"lineno\": 38,\r\n" + 
				"                        \"n\": \"\\n    Searches for the Python library, e.g. libpython<version>.so.\\n    Used by setup.py to set PYTHON_LDLIBRARY, and by scripts to set up LD_PRELOAD.\\n    \",\r\n" + 
				"                        \"s\": \"\\n    Searches for the Python library, e.g. libpython<version>.so.\\n    Used by setup.py to set PYTHON_LDLIBRARY, and by scripts to set up LD_PRELOAD.\\n    \",\r\n" + 
				"                        \"value\": \"\\n    Searches for the Python library, e.g. libpython<version>.so.\\n    Used by setup.py to set PYTHON_LDLIBRARY, and by scripts to set up LD_PRELOAD.\\n    \"\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Assign\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 47,\r\n" + 
				"                    \"end_lineno\": 42,\r\n" + 
				"                    \"lineno\": 42,\r\n" + 
				"                    \"targets\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 4,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Store\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 10,\r\n" + 
				"                            \"end_lineno\": 42,\r\n" + 
				"                            \"id\": \"libdir\",\r\n" + 
				"                            \"lineno\": 42\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"type_comment\": null,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 38,\r\n" + 
				"                                \"end_col_offset\": 46,\r\n" + 
				"                                \"end_lineno\": 42,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 42,\r\n" + 
				"                                \"n\": \"LIBDIR\",\r\n" + 
				"                                \"s\": \"LIBDIR\",\r\n" + 
				"                                \"value\": \"LIBDIR\"\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 13,\r\n" + 
				"                        \"end_col_offset\": 47,\r\n" + 
				"                        \"end_lineno\": 42,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"get_config_var\",\r\n" + 
				"                            \"col_offset\": 13,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 37,\r\n" + 
				"                            \"end_lineno\": 42,\r\n" + 
				"                            \"lineno\": 42,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 13,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 22,\r\n" + 
				"                                \"end_lineno\": 42,\r\n" + 
				"                                \"id\": \"sysconfig\",\r\n" + 
				"                                \"lineno\": 42\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 42\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Assign\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 53,\r\n" + 
				"                    \"end_lineno\": 43,\r\n" + 
				"                    \"lineno\": 43,\r\n" + 
				"                    \"targets\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 4,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Store\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 13,\r\n" + 
				"                            \"end_lineno\": 43,\r\n" + 
				"                            \"id\": \"ldlibrary\",\r\n" + 
				"                            \"lineno\": 43\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"type_comment\": null,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 41,\r\n" + 
				"                                \"end_col_offset\": 52,\r\n" + 
				"                                \"end_lineno\": 43,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 43,\r\n" + 
				"                                \"n\": \"LDLIBRARY\",\r\n" + 
				"                                \"s\": \"LDLIBRARY\",\r\n" + 
				"                                \"value\": \"LDLIBRARY\"\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 16,\r\n" + 
				"                        \"end_col_offset\": 53,\r\n" + 
				"                        \"end_lineno\": 43,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"get_config_var\",\r\n" + 
				"                            \"col_offset\": 16,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 40,\r\n" + 
				"                            \"end_lineno\": 43,\r\n" + 
				"                            \"lineno\": 43,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 16,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 25,\r\n" + 
				"                                \"end_lineno\": 43,\r\n" + 
				"                                \"id\": \"sysconfig\",\r\n" + 
				"                                \"lineno\": 43\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 43\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Return\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 19,\r\n" + 
				"                            \"end_lineno\": 45,\r\n" + 
				"                            \"lineno\": 45,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 15,\r\n" + 
				"                                \"end_col_offset\": 19,\r\n" + 
				"                                \"end_lineno\": 45,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 45,\r\n" + 
				"                                \"n\": null,\r\n" + 
				"                                \"s\": null,\r\n" + 
				"                                \"value\": null\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 19,\r\n" + 
				"                    \"end_lineno\": 45,\r\n" + 
				"                    \"lineno\": 44,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"BoolOp\",\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"end_col_offset\": 42,\r\n" + 
				"                        \"end_lineno\": 44,\r\n" + 
				"                        \"lineno\": 44,\r\n" + 
				"                        \"op\": {\r\n" + 
				"                            \"_type\": \"Or\"\r\n" + 
				"                        },\r\n" + 
				"                        \"values\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Compare\",\r\n" + 
				"                                \"col_offset\": 7,\r\n" + 
				"                                \"comparators\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Constant\",\r\n" + 
				"                                        \"col_offset\": 17,\r\n" + 
				"                                        \"end_col_offset\": 21,\r\n" + 
				"                                        \"end_lineno\": 44,\r\n" + 
				"                                        \"kind\": null,\r\n" + 
				"                                        \"lineno\": 44,\r\n" + 
				"                                        \"n\": null,\r\n" + 
				"                                        \"s\": null,\r\n" + 
				"                                        \"value\": null\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"end_col_offset\": 21,\r\n" + 
				"                                \"end_lineno\": 44,\r\n" + 
				"                                \"left\": {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 7,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 13,\r\n" + 
				"                                    \"end_lineno\": 44,\r\n" + 
				"                                    \"id\": \"libdir\",\r\n" + 
				"                                    \"lineno\": 44\r\n" + 
				"                                },\r\n" + 
				"                                \"lineno\": 44,\r\n" + 
				"                                \"ops\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Is\"\r\n" + 
				"                                    }\r\n" + 
				"                                ]\r\n" + 
				"                            },\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Compare\",\r\n" + 
				"                                \"col_offset\": 25,\r\n" + 
				"                                \"comparators\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Constant\",\r\n" + 
				"                                        \"col_offset\": 38,\r\n" + 
				"                                        \"end_col_offset\": 42,\r\n" + 
				"                                        \"end_lineno\": 44,\r\n" + 
				"                                        \"kind\": null,\r\n" + 
				"                                        \"lineno\": 44,\r\n" + 
				"                                        \"n\": null,\r\n" + 
				"                                        \"s\": null,\r\n" + 
				"                                        \"value\": null\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"end_col_offset\": 42,\r\n" + 
				"                                \"end_lineno\": 44,\r\n" + 
				"                                \"left\": {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 25,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 34,\r\n" + 
				"                                    \"end_lineno\": 44,\r\n" + 
				"                                    \"id\": \"ldlibrary\",\r\n" + 
				"                                    \"lineno\": 44\r\n" + 
				"                                },\r\n" + 
				"                                \"lineno\": 44,\r\n" + 
				"                                \"ops\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Is\"\r\n" + 
				"                                    }\r\n" + 
				"                                ]\r\n" + 
				"                            }\r\n" + 
				"                        ]\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Assign\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 48,\r\n" + 
				"                    \"end_lineno\": 47,\r\n" + 
				"                    \"lineno\": 47,\r\n" + 
				"                    \"targets\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 4,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Store\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 14,\r\n" + 
				"                            \"end_lineno\": 47,\r\n" + 
				"                            \"id\": \"lib_python\",\r\n" + 
				"                            \"lineno\": 47\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"type_comment\": null,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 30,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 36,\r\n" + 
				"                                \"end_lineno\": 47,\r\n" + 
				"                                \"id\": \"libdir\",\r\n" + 
				"                                \"lineno\": 47\r\n" + 
				"                            },\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 38,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 47,\r\n" + 
				"                                \"end_lineno\": 47,\r\n" + 
				"                                \"id\": \"ldlibrary\",\r\n" + 
				"                                \"lineno\": 47\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 17,\r\n" + 
				"                        \"end_col_offset\": 48,\r\n" + 
				"                        \"end_lineno\": 47,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"join\",\r\n" + 
				"                            \"col_offset\": 17,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 29,\r\n" + 
				"                            \"end_lineno\": 47,\r\n" + 
				"                            \"lineno\": 47,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Attribute\",\r\n" + 
				"                                \"attr\": \"path\",\r\n" + 
				"                                \"col_offset\": 17,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 24,\r\n" + 
				"                                \"end_lineno\": 47,\r\n" + 
				"                                \"lineno\": 47,\r\n" + 
				"                                \"value\": {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 17,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 19,\r\n" + 
				"                                    \"end_lineno\": 47,\r\n" + 
				"                                    \"id\": \"os\",\r\n" + 
				"                                    \"lineno\": 47\r\n" + 
				"                                }\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 47\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Return\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 25,\r\n" + 
				"                            \"end_lineno\": 49,\r\n" + 
				"                            \"lineno\": 49,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 15,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 25,\r\n" + 
				"                                \"end_lineno\": 49,\r\n" + 
				"                                \"id\": \"lib_python\",\r\n" + 
				"                                \"lineno\": 49\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 25,\r\n" + 
				"                    \"end_lineno\": 49,\r\n" + 
				"                    \"lineno\": 48,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 22,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 32,\r\n" + 
				"                                \"end_lineno\": 48,\r\n" + 
				"                                \"id\": \"lib_python\",\r\n" + 
				"                                \"lineno\": 48\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"end_col_offset\": 33,\r\n" + 
				"                        \"end_lineno\": 48,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"exists\",\r\n" + 
				"                            \"col_offset\": 7,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 21,\r\n" + 
				"                            \"end_lineno\": 48,\r\n" + 
				"                            \"lineno\": 48,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Attribute\",\r\n" + 
				"                                \"attr\": \"path\",\r\n" + 
				"                                \"col_offset\": 7,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 14,\r\n" + 
				"                                \"end_lineno\": 48,\r\n" + 
				"                                \"lineno\": 48,\r\n" + 
				"                                \"value\": {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 7,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 9,\r\n" + 
				"                                    \"end_lineno\": 48,\r\n" + 
				"                                    \"id\": \"os\",\r\n" + 
				"                                    \"lineno\": 48\r\n" + 
				"                                }\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 48\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Assign\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 53,\r\n" + 
				"                    \"end_lineno\": 52,\r\n" + 
				"                    \"lineno\": 52,\r\n" + 
				"                    \"targets\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 4,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Store\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 13,\r\n" + 
				"                            \"end_lineno\": 52,\r\n" + 
				"                            \"id\": \"multiarch\",\r\n" + 
				"                            \"lineno\": 52\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"type_comment\": null,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 41,\r\n" + 
				"                                \"end_col_offset\": 52,\r\n" + 
				"                                \"end_lineno\": 52,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 52,\r\n" + 
				"                                \"n\": \"MULTIARCH\",\r\n" + 
				"                                \"s\": \"MULTIARCH\",\r\n" + 
				"                                \"value\": \"MULTIARCH\"\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 16,\r\n" + 
				"                        \"end_col_offset\": 53,\r\n" + 
				"                        \"end_lineno\": 52,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"get_config_var\",\r\n" + 
				"                            \"col_offset\": 16,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 40,\r\n" + 
				"                            \"end_lineno\": 52,\r\n" + 
				"                            \"lineno\": 52,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 16,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 25,\r\n" + 
				"                                \"end_lineno\": 52,\r\n" + 
				"                                \"id\": \"sysconfig\",\r\n" + 
				"                                \"lineno\": 52\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 52\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Assign\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 63,\r\n" + 
				"                            \"end_lineno\": 54,\r\n" + 
				"                            \"lineno\": 54,\r\n" + 
				"                            \"targets\": [\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 8,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Store\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 18,\r\n" + 
				"                                    \"end_lineno\": 54,\r\n" + 
				"                                    \"id\": \"lib_python\",\r\n" + 
				"                                    \"lineno\": 54\r\n" + 
				"                                }\r\n" + 
				"                            ],\r\n" + 
				"                            \"type_comment\": null,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Call\",\r\n" + 
				"                                \"args\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 34,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 40,\r\n" + 
				"                                        \"end_lineno\": 54,\r\n" + 
				"                                        \"id\": \"libdir\",\r\n" + 
				"                                        \"lineno\": 54\r\n" + 
				"                                    },\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 42,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 51,\r\n" + 
				"                                        \"end_lineno\": 54,\r\n" + 
				"                                        \"id\": \"multiarch\",\r\n" + 
				"                                        \"lineno\": 54\r\n" + 
				"                                    },\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 53,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 62,\r\n" + 
				"                                        \"end_lineno\": 54,\r\n" + 
				"                                        \"id\": \"ldlibrary\",\r\n" + 
				"                                        \"lineno\": 54\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"col_offset\": 21,\r\n" + 
				"                                \"end_col_offset\": 63,\r\n" + 
				"                                \"end_lineno\": 54,\r\n" + 
				"                                \"func\": {\r\n" + 
				"                                    \"_type\": \"Attribute\",\r\n" + 
				"                                    \"attr\": \"join\",\r\n" + 
				"                                    \"col_offset\": 21,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 33,\r\n" + 
				"                                    \"end_lineno\": 54,\r\n" + 
				"                                    \"lineno\": 54,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Attribute\",\r\n" + 
				"                                        \"attr\": \"path\",\r\n" + 
				"                                        \"col_offset\": 21,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 28,\r\n" + 
				"                                        \"end_lineno\": 54,\r\n" + 
				"                                        \"lineno\": 54,\r\n" + 
				"                                        \"value\": {\r\n" + 
				"                                            \"_type\": \"Name\",\r\n" + 
				"                                            \"col_offset\": 21,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 23,\r\n" + 
				"                                            \"end_lineno\": 54,\r\n" + 
				"                                            \"id\": \"os\",\r\n" + 
				"                                            \"lineno\": 54\r\n" + 
				"                                        }\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"keywords\": [],\r\n" + 
				"                                \"lineno\": 54\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"If\",\r\n" + 
				"                            \"body\": [\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"Return\",\r\n" + 
				"                                    \"col_offset\": 12,\r\n" + 
				"                                    \"end_col_offset\": 29,\r\n" + 
				"                                    \"end_lineno\": 56,\r\n" + 
				"                                    \"lineno\": 56,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 19,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 29,\r\n" + 
				"                                        \"end_lineno\": 56,\r\n" + 
				"                                        \"id\": \"lib_python\",\r\n" + 
				"                                        \"lineno\": 56\r\n" + 
				"                                    }\r\n" + 
				"                                }\r\n" + 
				"                            ],\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 29,\r\n" + 
				"                            \"end_lineno\": 56,\r\n" + 
				"                            \"lineno\": 55,\r\n" + 
				"                            \"orelse\": [],\r\n" + 
				"                            \"test\": {\r\n" + 
				"                                \"_type\": \"Call\",\r\n" + 
				"                                \"args\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 26,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 36,\r\n" + 
				"                                        \"end_lineno\": 55,\r\n" + 
				"                                        \"id\": \"lib_python\",\r\n" + 
				"                                        \"lineno\": 55\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"col_offset\": 11,\r\n" + 
				"                                \"end_col_offset\": 37,\r\n" + 
				"                                \"end_lineno\": 55,\r\n" + 
				"                                \"func\": {\r\n" + 
				"                                    \"_type\": \"Attribute\",\r\n" + 
				"                                    \"attr\": \"exists\",\r\n" + 
				"                                    \"col_offset\": 11,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 25,\r\n" + 
				"                                    \"end_lineno\": 55,\r\n" + 
				"                                    \"lineno\": 55,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Attribute\",\r\n" + 
				"                                        \"attr\": \"path\",\r\n" + 
				"                                        \"col_offset\": 11,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 18,\r\n" + 
				"                                        \"end_lineno\": 55,\r\n" + 
				"                                        \"lineno\": 55,\r\n" + 
				"                                        \"value\": {\r\n" + 
				"                                            \"_type\": \"Name\",\r\n" + 
				"                                            \"col_offset\": 11,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 13,\r\n" + 
				"                                            \"end_lineno\": 55,\r\n" + 
				"                                            \"id\": \"os\",\r\n" + 
				"                                            \"lineno\": 55\r\n" + 
				"                                        }\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"keywords\": [],\r\n" + 
				"                                \"lineno\": 55\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 29,\r\n" + 
				"                    \"end_lineno\": 56,\r\n" + 
				"                    \"lineno\": 53,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"Compare\",\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"comparators\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 24,\r\n" + 
				"                                \"end_col_offset\": 28,\r\n" + 
				"                                \"end_lineno\": 53,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 53,\r\n" + 
				"                                \"n\": null,\r\n" + 
				"                                \"s\": null,\r\n" + 
				"                                \"value\": null\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"end_col_offset\": 28,\r\n" + 
				"                        \"end_lineno\": 53,\r\n" + 
				"                        \"left\": {\r\n" + 
				"                            \"_type\": \"Name\",\r\n" + 
				"                            \"col_offset\": 7,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 16,\r\n" + 
				"                            \"end_lineno\": 53,\r\n" + 
				"                            \"id\": \"multiarch\",\r\n" + 
				"                            \"lineno\": 53\r\n" + 
				"                        },\r\n" + 
				"                        \"lineno\": 53,\r\n" + 
				"                        \"ops\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"IsNot\"\r\n" + 
				"                            }\r\n" + 
				"                        ]\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"If\",\r\n" + 
				"                    \"body\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Assign\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 40,\r\n" + 
				"                            \"end_lineno\": 62,\r\n" + 
				"                            \"lineno\": 62,\r\n" + 
				"                            \"targets\": [\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 8,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Store\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 16,\r\n" + 
				"                                    \"end_lineno\": 62,\r\n" + 
				"                                    \"id\": \"ldshared\",\r\n" + 
				"                                    \"lineno\": 62\r\n" + 
				"                                }\r\n" + 
				"                            ],\r\n" + 
				"                            \"type_comment\": null,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"BinOp\",\r\n" + 
				"                                \"col_offset\": 19,\r\n" + 
				"                                \"end_col_offset\": 40,\r\n" + 
				"                                \"end_lineno\": 62,\r\n" + 
				"                                \"left\": {\r\n" + 
				"                                    \"_type\": \"Subscript\",\r\n" + 
				"                                    \"col_offset\": 19,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 33,\r\n" + 
				"                                    \"end_lineno\": 62,\r\n" + 
				"                                    \"lineno\": 62,\r\n" + 
				"                                    \"slice\": {\r\n" + 
				"                                        \"_type\": \"Slice\",\r\n" + 
				"                                        \"col_offset\": 29,\r\n" + 
				"                                        \"end_col_offset\": 32,\r\n" + 
				"                                        \"end_lineno\": 62,\r\n" + 
				"                                        \"lineno\": 62,\r\n" + 
				"                                        \"lower\": null,\r\n" + 
				"                                        \"step\": null,\r\n" + 
				"                                        \"upper\": {\r\n" + 
				"                                            \"_type\": \"UnaryOp\",\r\n" + 
				"                                            \"col_offset\": 30,\r\n" + 
				"                                            \"end_col_offset\": 32,\r\n" + 
				"                                            \"end_lineno\": 62,\r\n" + 
				"                                            \"lineno\": 62,\r\n" + 
				"                                            \"op\": {\r\n" + 
				"                                                \"_type\": \"USub\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"operand\": {\r\n" + 
				"                                                \"_type\": \"Constant\",\r\n" + 
				"                                                \"col_offset\": 31,\r\n" + 
				"                                                \"end_col_offset\": 32,\r\n" + 
				"                                                \"end_lineno\": 62,\r\n" + 
				"                                                \"kind\": null,\r\n" + 
				"                                                \"lineno\": 62,\r\n" + 
				"                                                \"n\": 1,\r\n" + 
				"                                                \"s\": 1,\r\n" + 
				"                                                \"value\": 1\r\n" + 
				"                                            }\r\n" + 
				"                                        }\r\n" + 
				"                                    },\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 19,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 28,\r\n" + 
				"                                        \"end_lineno\": 62,\r\n" + 
				"                                        \"id\": \"ldlibrary\",\r\n" + 
				"                                        \"lineno\": 62\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"lineno\": 62,\r\n" + 
				"                                \"op\": {\r\n" + 
				"                                    \"_type\": \"Add\"\r\n" + 
				"                                },\r\n" + 
				"                                \"right\": {\r\n" + 
				"                                    \"_type\": \"Constant\",\r\n" + 
				"                                    \"col_offset\": 36,\r\n" + 
				"                                    \"end_col_offset\": 40,\r\n" + 
				"                                    \"end_lineno\": 62,\r\n" + 
				"                                    \"kind\": null,\r\n" + 
				"                                    \"lineno\": 62,\r\n" + 
				"                                    \"n\": \"so\",\r\n" + 
				"                                    \"s\": \"so\",\r\n" + 
				"                                    \"value\": \"so\"\r\n" + 
				"                                }\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"Assign\",\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 51,\r\n" + 
				"                            \"end_lineno\": 63,\r\n" + 
				"                            \"lineno\": 63,\r\n" + 
				"                            \"targets\": [\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 8,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Store\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 18,\r\n" + 
				"                                    \"end_lineno\": 63,\r\n" + 
				"                                    \"id\": \"lib_python\",\r\n" + 
				"                                    \"lineno\": 63\r\n" + 
				"                                }\r\n" + 
				"                            ],\r\n" + 
				"                            \"type_comment\": null,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Call\",\r\n" + 
				"                                \"args\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 34,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 40,\r\n" + 
				"                                        \"end_lineno\": 63,\r\n" + 
				"                                        \"id\": \"libdir\",\r\n" + 
				"                                        \"lineno\": 63\r\n" + 
				"                                    },\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 42,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 50,\r\n" + 
				"                                        \"end_lineno\": 63,\r\n" + 
				"                                        \"id\": \"ldshared\",\r\n" + 
				"                                        \"lineno\": 63\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"col_offset\": 21,\r\n" + 
				"                                \"end_col_offset\": 51,\r\n" + 
				"                                \"end_lineno\": 63,\r\n" + 
				"                                \"func\": {\r\n" + 
				"                                    \"_type\": \"Attribute\",\r\n" + 
				"                                    \"attr\": \"join\",\r\n" + 
				"                                    \"col_offset\": 21,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 33,\r\n" + 
				"                                    \"end_lineno\": 63,\r\n" + 
				"                                    \"lineno\": 63,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Attribute\",\r\n" + 
				"                                        \"attr\": \"path\",\r\n" + 
				"                                        \"col_offset\": 21,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 28,\r\n" + 
				"                                        \"end_lineno\": 63,\r\n" + 
				"                                        \"lineno\": 63,\r\n" + 
				"                                        \"value\": {\r\n" + 
				"                                            \"_type\": \"Name\",\r\n" + 
				"                                            \"col_offset\": 21,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 23,\r\n" + 
				"                                            \"end_lineno\": 63,\r\n" + 
				"                                            \"id\": \"os\",\r\n" + 
				"                                            \"lineno\": 63\r\n" + 
				"                                        }\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"keywords\": [],\r\n" + 
				"                                \"lineno\": 63\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"If\",\r\n" + 
				"                            \"body\": [\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"Return\",\r\n" + 
				"                                    \"col_offset\": 12,\r\n" + 
				"                                    \"end_col_offset\": 29,\r\n" + 
				"                                    \"end_lineno\": 65,\r\n" + 
				"                                    \"lineno\": 65,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 19,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 29,\r\n" + 
				"                                        \"end_lineno\": 65,\r\n" + 
				"                                        \"id\": \"lib_python\",\r\n" + 
				"                                        \"lineno\": 65\r\n" + 
				"                                    }\r\n" + 
				"                                }\r\n" + 
				"                            ],\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 29,\r\n" + 
				"                            \"end_lineno\": 65,\r\n" + 
				"                            \"lineno\": 64,\r\n" + 
				"                            \"orelse\": [],\r\n" + 
				"                            \"test\": {\r\n" + 
				"                                \"_type\": \"Call\",\r\n" + 
				"                                \"args\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Name\",\r\n" + 
				"                                        \"col_offset\": 26,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 36,\r\n" + 
				"                                        \"end_lineno\": 64,\r\n" + 
				"                                        \"id\": \"lib_python\",\r\n" + 
				"                                        \"lineno\": 64\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"col_offset\": 11,\r\n" + 
				"                                \"end_col_offset\": 37,\r\n" + 
				"                                \"end_lineno\": 64,\r\n" + 
				"                                \"func\": {\r\n" + 
				"                                    \"_type\": \"Attribute\",\r\n" + 
				"                                    \"attr\": \"exists\",\r\n" + 
				"                                    \"col_offset\": 11,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 25,\r\n" + 
				"                                    \"end_lineno\": 64,\r\n" + 
				"                                    \"lineno\": 64,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Attribute\",\r\n" + 
				"                                        \"attr\": \"path\",\r\n" + 
				"                                        \"col_offset\": 11,\r\n" + 
				"                                        \"ctx\": {\r\n" + 
				"                                            \"_type\": \"Load\"\r\n" + 
				"                                        },\r\n" + 
				"                                        \"end_col_offset\": 18,\r\n" + 
				"                                        \"end_lineno\": 64,\r\n" + 
				"                                        \"lineno\": 64,\r\n" + 
				"                                        \"value\": {\r\n" + 
				"                                            \"_type\": \"Name\",\r\n" + 
				"                                            \"col_offset\": 11,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 13,\r\n" + 
				"                                            \"end_lineno\": 64,\r\n" + 
				"                                            \"id\": \"os\",\r\n" + 
				"                                            \"lineno\": 64\r\n" + 
				"                                        }\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                \"keywords\": [],\r\n" + 
				"                                \"lineno\": 64\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"_type\": \"If\",\r\n" + 
				"                            \"body\": [\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"Assign\",\r\n" + 
				"                                    \"col_offset\": 12,\r\n" + 
				"                                    \"end_col_offset\": 66,\r\n" + 
				"                                    \"end_lineno\": 67,\r\n" + 
				"                                    \"lineno\": 67,\r\n" + 
				"                                    \"targets\": [\r\n" + 
				"                                        {\r\n" + 
				"                                            \"_type\": \"Name\",\r\n" + 
				"                                            \"col_offset\": 12,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Store\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 22,\r\n" + 
				"                                            \"end_lineno\": 67,\r\n" + 
				"                                            \"id\": \"lib_python\",\r\n" + 
				"                                            \"lineno\": 67\r\n" + 
				"                                        }\r\n" + 
				"                                    ],\r\n" + 
				"                                    \"type_comment\": null,\r\n" + 
				"                                    \"value\": {\r\n" + 
				"                                        \"_type\": \"Call\",\r\n" + 
				"                                        \"args\": [\r\n" + 
				"                                            {\r\n" + 
				"                                                \"_type\": \"Name\",\r\n" + 
				"                                                \"col_offset\": 38,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 44,\r\n" + 
				"                                                \"end_lineno\": 67,\r\n" + 
				"                                                \"id\": \"libdir\",\r\n" + 
				"                                                \"lineno\": 67\r\n" + 
				"                                            },\r\n" + 
				"                                            {\r\n" + 
				"                                                \"_type\": \"Name\",\r\n" + 
				"                                                \"col_offset\": 46,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 55,\r\n" + 
				"                                                \"end_lineno\": 67,\r\n" + 
				"                                                \"id\": \"multiarch\",\r\n" + 
				"                                                \"lineno\": 67\r\n" + 
				"                                            },\r\n" + 
				"                                            {\r\n" + 
				"                                                \"_type\": \"Name\",\r\n" + 
				"                                                \"col_offset\": 57,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 65,\r\n" + 
				"                                                \"end_lineno\": 67,\r\n" + 
				"                                                \"id\": \"ldshared\",\r\n" + 
				"                                                \"lineno\": 67\r\n" + 
				"                                            }\r\n" + 
				"                                        ],\r\n" + 
				"                                        \"col_offset\": 25,\r\n" + 
				"                                        \"end_col_offset\": 66,\r\n" + 
				"                                        \"end_lineno\": 67,\r\n" + 
				"                                        \"func\": {\r\n" + 
				"                                            \"_type\": \"Attribute\",\r\n" + 
				"                                            \"attr\": \"join\",\r\n" + 
				"                                            \"col_offset\": 25,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 37,\r\n" + 
				"                                            \"end_lineno\": 67,\r\n" + 
				"                                            \"lineno\": 67,\r\n" + 
				"                                            \"value\": {\r\n" + 
				"                                                \"_type\": \"Attribute\",\r\n" + 
				"                                                \"attr\": \"path\",\r\n" + 
				"                                                \"col_offset\": 25,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 32,\r\n" + 
				"                                                \"end_lineno\": 67,\r\n" + 
				"                                                \"lineno\": 67,\r\n" + 
				"                                                \"value\": {\r\n" + 
				"                                                    \"_type\": \"Name\",\r\n" + 
				"                                                    \"col_offset\": 25,\r\n" + 
				"                                                    \"ctx\": {\r\n" + 
				"                                                        \"_type\": \"Load\"\r\n" + 
				"                                                    },\r\n" + 
				"                                                    \"end_col_offset\": 27,\r\n" + 
				"                                                    \"end_lineno\": 67,\r\n" + 
				"                                                    \"id\": \"os\",\r\n" + 
				"                                                    \"lineno\": 67\r\n" + 
				"                                                }\r\n" + 
				"                                            }\r\n" + 
				"                                        },\r\n" + 
				"                                        \"keywords\": [],\r\n" + 
				"                                        \"lineno\": 67\r\n" + 
				"                                    }\r\n" + 
				"                                },\r\n" + 
				"                                {\r\n" + 
				"                                    \"_type\": \"If\",\r\n" + 
				"                                    \"body\": [\r\n" + 
				"                                        {\r\n" + 
				"                                            \"_type\": \"Return\",\r\n" + 
				"                                            \"col_offset\": 16,\r\n" + 
				"                                            \"end_col_offset\": 33,\r\n" + 
				"                                            \"end_lineno\": 69,\r\n" + 
				"                                            \"lineno\": 69,\r\n" + 
				"                                            \"value\": {\r\n" + 
				"                                                \"_type\": \"Name\",\r\n" + 
				"                                                \"col_offset\": 23,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 33,\r\n" + 
				"                                                \"end_lineno\": 69,\r\n" + 
				"                                                \"id\": \"lib_python\",\r\n" + 
				"                                                \"lineno\": 69\r\n" + 
				"                                            }\r\n" + 
				"                                        }\r\n" + 
				"                                    ],\r\n" + 
				"                                    \"col_offset\": 12,\r\n" + 
				"                                    \"end_col_offset\": 33,\r\n" + 
				"                                    \"end_lineno\": 69,\r\n" + 
				"                                    \"lineno\": 68,\r\n" + 
				"                                    \"orelse\": [],\r\n" + 
				"                                    \"test\": {\r\n" + 
				"                                        \"_type\": \"Call\",\r\n" + 
				"                                        \"args\": [\r\n" + 
				"                                            {\r\n" + 
				"                                                \"_type\": \"Name\",\r\n" + 
				"                                                \"col_offset\": 30,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 40,\r\n" + 
				"                                                \"end_lineno\": 68,\r\n" + 
				"                                                \"id\": \"lib_python\",\r\n" + 
				"                                                \"lineno\": 68\r\n" + 
				"                                            }\r\n" + 
				"                                        ],\r\n" + 
				"                                        \"col_offset\": 15,\r\n" + 
				"                                        \"end_col_offset\": 41,\r\n" + 
				"                                        \"end_lineno\": 68,\r\n" + 
				"                                        \"func\": {\r\n" + 
				"                                            \"_type\": \"Attribute\",\r\n" + 
				"                                            \"attr\": \"exists\",\r\n" + 
				"                                            \"col_offset\": 15,\r\n" + 
				"                                            \"ctx\": {\r\n" + 
				"                                                \"_type\": \"Load\"\r\n" + 
				"                                            },\r\n" + 
				"                                            \"end_col_offset\": 29,\r\n" + 
				"                                            \"end_lineno\": 68,\r\n" + 
				"                                            \"lineno\": 68,\r\n" + 
				"                                            \"value\": {\r\n" + 
				"                                                \"_type\": \"Attribute\",\r\n" + 
				"                                                \"attr\": \"path\",\r\n" + 
				"                                                \"col_offset\": 15,\r\n" + 
				"                                                \"ctx\": {\r\n" + 
				"                                                    \"_type\": \"Load\"\r\n" + 
				"                                                },\r\n" + 
				"                                                \"end_col_offset\": 22,\r\n" + 
				"                                                \"end_lineno\": 68,\r\n" + 
				"                                                \"lineno\": 68,\r\n" + 
				"                                                \"value\": {\r\n" + 
				"                                                    \"_type\": \"Name\",\r\n" + 
				"                                                    \"col_offset\": 15,\r\n" + 
				"                                                    \"ctx\": {\r\n" + 
				"                                                        \"_type\": \"Load\"\r\n" + 
				"                                                    },\r\n" + 
				"                                                    \"end_col_offset\": 17,\r\n" + 
				"                                                    \"end_lineno\": 68,\r\n" + 
				"                                                    \"id\": \"os\",\r\n" + 
				"                                                    \"lineno\": 68\r\n" + 
				"                                                }\r\n" + 
				"                                            }\r\n" + 
				"                                        },\r\n" + 
				"                                        \"keywords\": [],\r\n" + 
				"                                        \"lineno\": 68\r\n" + 
				"                                    }\r\n" + 
				"                                }\r\n" + 
				"                            ],\r\n" + 
				"                            \"col_offset\": 8,\r\n" + 
				"                            \"end_col_offset\": 33,\r\n" + 
				"                            \"end_lineno\": 69,\r\n" + 
				"                            \"lineno\": 66,\r\n" + 
				"                            \"orelse\": [],\r\n" + 
				"                            \"test\": {\r\n" + 
				"                                \"_type\": \"Compare\",\r\n" + 
				"                                \"col_offset\": 11,\r\n" + 
				"                                \"comparators\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"Constant\",\r\n" + 
				"                                        \"col_offset\": 28,\r\n" + 
				"                                        \"end_col_offset\": 32,\r\n" + 
				"                                        \"end_lineno\": 66,\r\n" + 
				"                                        \"kind\": null,\r\n" + 
				"                                        \"lineno\": 66,\r\n" + 
				"                                        \"n\": null,\r\n" + 
				"                                        \"s\": null,\r\n" + 
				"                                        \"value\": null\r\n" + 
				"                                    }\r\n" + 
				"                                ],\r\n" + 
				"                                \"end_col_offset\": 32,\r\n" + 
				"                                \"end_lineno\": 66,\r\n" + 
				"                                \"left\": {\r\n" + 
				"                                    \"_type\": \"Name\",\r\n" + 
				"                                    \"col_offset\": 11,\r\n" + 
				"                                    \"ctx\": {\r\n" + 
				"                                        \"_type\": \"Load\"\r\n" + 
				"                                    },\r\n" + 
				"                                    \"end_col_offset\": 20,\r\n" + 
				"                                    \"end_lineno\": 66,\r\n" + 
				"                                    \"id\": \"multiarch\",\r\n" + 
				"                                    \"lineno\": 66\r\n" + 
				"                                },\r\n" + 
				"                                \"lineno\": 66,\r\n" + 
				"                                \"ops\": [\r\n" + 
				"                                    {\r\n" + 
				"                                        \"_type\": \"IsNot\"\r\n" + 
				"                                    }\r\n" + 
				"                                ]\r\n" + 
				"                            }\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 33,\r\n" + 
				"                    \"end_lineno\": 69,\r\n" + 
				"                    \"lineno\": 61,\r\n" + 
				"                    \"orelse\": [],\r\n" + 
				"                    \"test\": {\r\n" + 
				"                        \"_type\": \"Call\",\r\n" + 
				"                        \"args\": [\r\n" + 
				"                            {\r\n" + 
				"                                \"_type\": \"Constant\",\r\n" + 
				"                                \"col_offset\": 26,\r\n" + 
				"                                \"end_col_offset\": 30,\r\n" + 
				"                                \"end_lineno\": 61,\r\n" + 
				"                                \"kind\": null,\r\n" + 
				"                                \"lineno\": 61,\r\n" + 
				"                                \"n\": \".a\",\r\n" + 
				"                                \"s\": \".a\",\r\n" + 
				"                                \"value\": \".a\"\r\n" + 
				"                            }\r\n" + 
				"                        ],\r\n" + 
				"                        \"col_offset\": 7,\r\n" + 
				"                        \"end_col_offset\": 31,\r\n" + 
				"                        \"end_lineno\": 61,\r\n" + 
				"                        \"func\": {\r\n" + 
				"                            \"_type\": \"Attribute\",\r\n" + 
				"                            \"attr\": \"endswith\",\r\n" + 
				"                            \"col_offset\": 7,\r\n" + 
				"                            \"ctx\": {\r\n" + 
				"                                \"_type\": \"Load\"\r\n" + 
				"                            },\r\n" + 
				"                            \"end_col_offset\": 25,\r\n" + 
				"                            \"end_lineno\": 61,\r\n" + 
				"                            \"lineno\": 61,\r\n" + 
				"                            \"value\": {\r\n" + 
				"                                \"_type\": \"Name\",\r\n" + 
				"                                \"col_offset\": 7,\r\n" + 
				"                                \"ctx\": {\r\n" + 
				"                                    \"_type\": \"Load\"\r\n" + 
				"                                },\r\n" + 
				"                                \"end_col_offset\": 16,\r\n" + 
				"                                \"end_lineno\": 61,\r\n" + 
				"                                \"id\": \"ldlibrary\",\r\n" + 
				"                                \"lineno\": 61\r\n" + 
				"                            }\r\n" + 
				"                        },\r\n" + 
				"                        \"keywords\": [],\r\n" + 
				"                        \"lineno\": 61\r\n" + 
				"                    }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                    \"_type\": \"Return\",\r\n" + 
				"                    \"col_offset\": 4,\r\n" + 
				"                    \"end_col_offset\": 15,\r\n" + 
				"                    \"end_lineno\": 72,\r\n" + 
				"                    \"lineno\": 72,\r\n" + 
				"                    \"value\": {\r\n" + 
				"                        \"_type\": \"Constant\",\r\n" + 
				"                        \"col_offset\": 11,\r\n" + 
				"                        \"end_col_offset\": 15,\r\n" + 
				"                        \"end_lineno\": 72,\r\n" + 
				"                        \"kind\": null,\r\n" + 
				"                        \"lineno\": 72,\r\n" + 
				"                        \"n\": null,\r\n" + 
				"                        \"s\": null,\r\n" + 
				"                        \"value\": null\r\n" + 
				"                    }\r\n" + 
				"                }\r\n" + 
				"            ],\r\n" + 
				"            \"col_offset\": 0,\r\n" + 
				"            \"decorator_list\": [],\r\n" + 
				"            \"end_col_offset\": 15,\r\n" + 
				"            \"end_lineno\": 72,\r\n" + 
				"            \"lineno\": 37,\r\n" + 
				"            \"name\": \"get_libpython\",\r\n" + 
				"            \"returns\": null,\r\n" + 
				"            \"type_comment\": null\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"type_ignores\": []\r\n" + 
				"}";	}
	
	
	public String getTreeFromFileAsString(String path) {
		try (Interpreter interp = new SharedInterpreter()) {
			 interp.exec("import ast");
			 interp.exec("file = open(\"" + path + "\", \"r\")");
			 interp.exec("data = file.read()");
			 interp.exec("file.close()");
			 interp.exec("tree = ast.parse(data)");
			 interp.exec("printTree = ast.dump(tree)");
			 Object object =  interp.getValue("printTree");
			 
			 return object.toString();
		}

	}
	
	public String getTreeFromFileAsJSON(String path) {
		try (Interpreter interp = new SharedInterpreter()) {
			 interp.exec("import json");
			 interp.exec("from ast import parse");
			 interp.exec("from ast2json import ast2json");
			 interp.exec("ast = ast2json(parse(open(\"C:/Users/david/Documents/Informatik/Hiwi/commands.py\").read()))");
			 interp.exec("tree = json.dumps(ast, indent=4)");
			 Object object = interp.getValue("tree");
			 
			 return object.toString();
		}

	}

}
